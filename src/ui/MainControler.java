package ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Window;
import poc.*;
import util.MyProxySelector;
import util.TongdaOA;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainControler {

    @FXML
    private ChoiceBox<String> vuls;

    @FXML
    private Button uploadFile;

    @FXML
    private TextField fileName;

    @FXML
    private TextArea uploadInfo;

    @FXML
    private TextField cookie;

    @FXML
    private TextArea checkInfo;

    @FXML
    private Button check;

    @FXML
    private TextField url;

    @FXML
    private Button proxyBtn;

    @FXML
    private Label proxyStatus;


    //检查漏洞
    @FXML
    void checkVul(ActionEvent event) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if(url.getText().isEmpty())   {
                    checkInfo.setText("url为空，请输入url！！！");
                }
                else{
                    init();//初始化对象
                    switch (vuls.getValue()) {
                        case "ALL":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() ? "⚠" + url.getText() + "存在通达OA任意用户登录漏洞\n" : "[-]"+url.getText() + "不存在通达OA任意用户登录漏洞\n");
                            cookie.setText(tongdaOA_anyUserLogin.getCookie());
                            tongdaOA_imUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() && tongdaOA_imUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台im任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台im任意文件上传漏洞\n");
                            tongdaOA_anyUserLogin.POC2();
                            tongdaOA_module_upload.setCookie(tongdaOA_anyUserLogin.getCookie());
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() && tongdaOA_module_upload.POC() ? "⚠" + url.getText() + "存在通达OA后台module任意文件上传漏洞\n" :"[-]"+ url.getText() + "不存在通达OA后台module任意文件上传漏洞\n");
                            tongdaOA_ispiritUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() && tongdaOA_ispiritUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台ispirit任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台ispirit任意文件上传漏洞\n");
                            checkInfo.appendText(tongdaOA_ispiritFileInclude.POC() ? "⚠" + url.getText() + "存在通达OA前台ispirit文件包含漏洞\n" : "[-]"+url.getText() + "不存在通达OA前台ispirit文件包含漏洞\n");
                            checkInfo.appendText(tongdaOA_actionUpload.POC() ? "⚠" + url.getText() + "存在通达OA前台action文件包含漏洞\n" : "[-]"+url.getText() + "不存在通达OA前台action文件包含漏洞\n");
                            checkInfo.appendText(tongdaOA_authmobiLogin.POC() ? "⚠" + url.getText() + "存在通达OAauthmobi伪造在线用户登录漏洞\n": "[-]"+url.getText() + "不存在通达OAauthmobi伪造在线用户登录漏洞\n");
                            if (tongdaOA_anyUserLogin.POC2()){
                                tongdaOA_attachment_remark_fileInclude.setCookie(tongdaOA_anyUserLogin.getCookie());
                                checkInfo.appendText(tongdaOA_attachment_remark_fileInclude.POC()?"⚠" + url.getText() + "存在通达OA后台update任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                            } else if (tongdaOA_authmobiLogin.POC()){
                                tongdaOA_attachment_remark_fileInclude.setCookie(tongdaOA_authmobiLogin.getCookie());
                                checkInfo.appendText(tongdaOA_attachment_remark_fileInclude.POC()?"⚠" + url.getText() + "存在通达OA后台update任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                            } else {
                                checkInfo.appendText("[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                            }
                            if (tongdaOA_anyUserLogin.POC2()){
                                tongdaOA_managementUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                                checkInfo.appendText(tongdaOA_managementUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台management任意文件上传漏洞\n" :"[-]"+ url.getText() + "不存在通达OA后台management任意文件上传漏洞\n");
                            }else if (tongdaOA_authmobiLogin.POC()){
                                tongdaOA_managementUpload.setCookie(tongdaOA_authmobiLogin.getCookie());
                                checkInfo.appendText(tongdaOA_managementUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台management任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台management任意文件上传漏洞\n");
                            }else {
                                checkInfo.appendText("[-]"+url.getText()+"不存在通达OA后台management任意文件上传漏洞\n");
                            }
                            checkInfo.appendText(tongdaOA_bypassAuth.POC() ? "⚠" + url.getText() + "存在通达OA登录认证绕过漏洞\n" : "[-]"+url.getText() + "不存在通达OA登录认证绕过漏洞\n");
                            if (tongdaOA_bypassAuth.POC()){
                                cookie.setText(tongdaOA_bypassAuth.getCookie());
                            }
                            checkInfo.appendText(tongdaOA_deleteAuth.POC() ? "⚠" + url.getText() + "存在通达OAV11.6前台任意文件删除&getshell漏洞\n" :"[-]"+ url.getText() + "不存在存在通达OAV11.6前台任意文件删除&getshell漏洞\n");
                            checkInfo.appendText(tongdaOA_GatewayUplaod.POC() ? "⚠" + url.getText() + "存在通达OA gateway前台任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA gateway前台任意文件上传漏洞\n");
                            checkInfo.appendText(tongdaOA_apiAliUpload.POC() ? "⚠" + url.getText() + "存在通达OA api_ali前台任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA api_ali前台任意文件上传漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Anyuser_Login":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() ? "⚠" + url.getText() + "存在通达OA任意用户登录漏洞\n" : "[-]"+url.getText() + "不存在通达OA任意用户登录漏洞\n");
                            cookie.setText(tongdaOA_anyUserLogin.getCookie());
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Im_Upload":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            tongdaOA_anyUserLogin.POC2();
                            tongdaOA_imUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() && tongdaOA_imUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台im任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台im任意文件上传漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Module_Upload":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            tongdaOA_anyUserLogin.POC2();
                            tongdaOA_module_upload.setCookie(tongdaOA_anyUserLogin.getCookie());
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() && tongdaOA_module_upload.POC() ? "⚠" + url.getText() + "存在通达OA后台module任意文件上传漏洞\n" :"[-]"+ url.getText() + "不存在通达OA后台module任意文件上传漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Ispirit_Upload":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            tongdaOA_anyUserLogin.POC2();
                            tongdaOA_ispiritUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                            checkInfo.appendText(tongdaOA_anyUserLogin.POC2() && tongdaOA_ispiritUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台ispirit任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台ispirit任意文件上传漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Ispirit_FileInclude":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_ispiritFileInclude.POC() ? "⚠" + url.getText() + "存在通达OA前台ispirit文件包含漏洞\n" : "[-]"+url.getText() + "不存在通达OA前台ispirit文件包含漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Action_Upload":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_actionUpload.POC() ? "⚠" + url.getText() + "存在通达OA前台action文件包含漏洞\n" : "[-]"+url.getText() + "不存在通达OA前台action文件包含漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Authmobi_Login":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_authmobiLogin.POC() ? "⚠" + url.getText() + "存在通达OAauthmobi伪造在线用户登录漏洞\n" +"管理员cookie:"+tongdaOA_authmobiLogin.getCookie()+"\n": "[-]"+url.getText() + "不存在通达OAauthmobi伪造在线用户登录漏洞\n");
                            cookie.setText(tongdaOA_authmobiLogin.getCookie());
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Attachement_Remark_FileInclude":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            if (tongdaOA_anyUserLogin.POC2()){
                                tongdaOA_attachment_remark_fileInclude.setCookie(tongdaOA_anyUserLogin.getCookie());
                                checkInfo.appendText(tongdaOA_attachment_remark_fileInclude.POC()?"⚠" + url.getText() + "存在通达OA后台update任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                                checkInfo.appendText("-------------检测完毕-------------");
                            } else if (tongdaOA_authmobiLogin.POC()){
                                tongdaOA_attachment_remark_fileInclude.setCookie(tongdaOA_authmobiLogin.getCookie());
                                checkInfo.appendText(tongdaOA_attachment_remark_fileInclude.POC()?"⚠" + url.getText() + "存在通达OA后台update任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                                checkInfo.appendText("-------------检测完毕-------------");
                            } else {
                                checkInfo.appendText("[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                                checkInfo.appendText("-------------检测完毕-------------");
                            }
                            break;
                        case "TongdaOA_Management_Upload":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            if (tongdaOA_anyUserLogin.POC2()){
                                tongdaOA_managementUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                                checkInfo.appendText(tongdaOA_managementUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台management任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台management任意文件上传漏洞\n");
                            }else if (tongdaOA_authmobiLogin.POC()){
                                tongdaOA_managementUpload.setCookie(tongdaOA_authmobiLogin.getCookie());
                                checkInfo.appendText(tongdaOA_managementUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台management任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA后台management任意文件上传漏洞\n");
                            }else {
                                checkInfo.appendText("[-]"+url.getText()+"不存在通达OA后台management任意文件上传漏洞\n");
                            }
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Bypass_Auth":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_bypassAuth.POC() ? "⚠" + url.getText() + "存在通达OA登录认证绕过漏洞\n" : "[-]"+url.getText() + "不存在通达OA登录认证绕过漏洞\n");
                            if (tongdaOA_bypassAuth.POC()){
                                cookie.setText(tongdaOA_bypassAuth.getCookie());
                            }
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Delete_Auth":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_deleteAuth.POC() ? "⚠" + url.getText() + "存在通达OAV11.6前台任意文件删除&getshell漏洞\n" : "[-]"+url.getText() + "不存在通达OAV11.6前台任意文件删除&getshell漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_gateway_Upload":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_GatewayUplaod.POC() ? "⚠" + url.getText() + "存在通达OA gateway前台任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA gateway前台任意文件上传漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                        case "TongdaOA_Api_Ali_Upload":
                            checkInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                            checkInfo.appendText(tongdaOA_apiAliUpload.POC() ? "⚠" + url.getText() + "存在通达OA api_ali前台任意文件上传漏洞\n" : "[-]"+url.getText() + "不存在通达OA api_ali前台任意文件上传漏洞\n");
                            checkInfo.appendText("-------------检测完毕-------------");
                            break;
                    }
                }
            }
        });

    }
    //上传文件
    @FXML
    void uploadFile(ActionEvent event) {
        init();
        switch (vuls.getValue()){
            case "ALL":
                uploadInfo.setText("请选择具体漏洞进行文件上传！！！");
                break;
            case "TongdaOA_Anyuser_Login":
                uploadInfo.setText("该漏洞不支持文件上传！！！");
                break;
            case "TongdaOA_Im_Upload":
                uploadInfo.setText("通达OA版本为："+tongdaOA.getVersion()+"\n");
                tongdaOA_anyUserLogin.POC2();
                tongdaOA_imUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                uploadInfo.appendText(tongdaOA_anyUserLogin.POC2()&&tongdaOA_imUpload.POC()?"⚠"+url.getText()+"存在通达OA后台im任意文件上传漏洞\n"+"蚁剑:"+tongdaOA_imUpload.getUrl()+tongdaOA_imUpload.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OA后台im任意文件上传漏洞\n");
                break;
            case "TongdaOA_Module_Upload":
                uploadInfo.setText("通达OA版本为："+tongdaOA.getVersion()+"\n");
                tongdaOA_anyUserLogin.POC2();
                tongdaOA_module_upload.setCookie(tongdaOA_anyUserLogin.getCookie());
                uploadInfo.appendText(tongdaOA_anyUserLogin.POC2()&&tongdaOA_module_upload.POC()?"⚠"+url.getText()+"存在通达OA后台module任意文件上传漏洞\n"+"蚁剑:"+tongdaOA_module_upload.getUrl()+tongdaOA_module_upload.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OA后台module任意文件上传漏洞\n");
                break;
            case "TongdaOA_Ispirit_Upload":
                uploadInfo.setText("通达OA版本为："+tongdaOA.getVersion()+"\n");
                tongdaOA_anyUserLogin.POC2();
                tongdaOA_ispiritUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                uploadInfo.appendText(tongdaOA_anyUserLogin.POC2()&&tongdaOA_ispiritUpload.POC()?"⚠"+url.getText()+"存在通达OA后台ispirit任意文件上传漏洞\n"+"蚁剑:"+tongdaOA_ispiritUpload.getUrl()+tongdaOA_ispiritUpload.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OA后台ispirit任意文件上传漏洞\n");
                break;
            case "TongdaOA_Ispirit_FileInclude":
                uploadInfo.setText("通达OA版本为："+tongdaOA.getVersion()+"\n");
                uploadInfo.appendText(tongdaOA_ispiritFileInclude.POC()?"⚠"+url.getText()+"存在通达OA前台ispirit文件包含漏洞\n"+"蚁剑:"+tongdaOA_ispiritUpload.getUrl()+tongdaOA_ispiritFileInclude.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OA前台ispirit文件包含漏洞\n");
                break;
            case "TongdaOA_Action_Upload":
                uploadInfo.setText("通达OA版本为："+tongdaOA.getVersion()+"\n");
                uploadInfo.appendText(tongdaOA_actionUpload.POC()?"⚠"+url.getText()+"存在通达OA前台action文件包含漏洞\n"+"蚁剑:"+tongdaOA_actionUpload.getUrl()+tongdaOA_actionUpload.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OA前台action文件包含漏洞\n");
                break;
            case "TongdaOA_Authmobi_Login":
                uploadInfo.setText("该漏洞不支持文件上传！！！");
                break;
            case "TongdaOA_Attachement_Remark_FileInclude":
                uploadInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                if (tongdaOA_anyUserLogin.POC2()){
                    tongdaOA_attachment_remark_fileInclude.setCookie(tongdaOA_anyUserLogin.getCookie());
                    uploadInfo.appendText(tongdaOA_attachment_remark_fileInclude.POC()?"⚠" + url.getText() + "存在通达OA后台update任意文件上传漏洞\n" +"蚁剑:"+tongdaOA_attachment_remark_fileInclude.getUrl()+tongdaOA_attachment_remark_fileInclude.getPath()+"\n"+"密码:x\n":"[-]"+ url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                } else if (tongdaOA_authmobiLogin.POC()){
                    tongdaOA_attachment_remark_fileInclude.setCookie(tongdaOA_authmobiLogin.getCookie());
                    uploadInfo.appendText(tongdaOA_attachment_remark_fileInclude.POC()?"⚠" + url.getText() + "存在通达OA后台update任意文件上传漏洞\n" +"蚁剑:"+tongdaOA_attachment_remark_fileInclude.getUrl()+tongdaOA_attachment_remark_fileInclude.getPath()+"\n"+"密码:x\n": "[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                } else {
                    checkInfo.appendText("[-]"+url.getText() + "不存在通达OA后台update任意文件上传漏洞\n");
                }
                break;
            case "TongdaOA_Management_Upload":
                uploadInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                if (tongdaOA_anyUserLogin.POC2()){
                    tongdaOA_managementUpload.setCookie(tongdaOA_anyUserLogin.getCookie());
                    uploadInfo.appendText(tongdaOA_managementUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台management任意文件上传漏洞\n"+"蚁剑:"+tongdaOA_managementUpload.getUrl()+tongdaOA_managementUpload.getPath()+"\n"+"密码:x\n": "[-]"+url.getText() + "不存在通达OA后台management任意文件上传漏洞\n");
                }else if (tongdaOA_authmobiLogin.POC()){
                    tongdaOA_managementUpload.setCookie(tongdaOA_authmobiLogin.getCookie());
                    uploadInfo.appendText(tongdaOA_managementUpload.POC() ? "⚠" + url.getText() + "存在通达OA后台management任意文件上传漏洞\n"+"蚁剑:"+tongdaOA_managementUpload.getUrl()+tongdaOA_managementUpload.getPath()+"\n"+"密码:x\n" : "[-]"+url.getText() + "不存在通达OA后台management任意文件上传漏洞\n");
                }else {
                    uploadInfo.appendText("[-]"+url.getText()+"不存在通达OA后台management任意文件上传漏洞\n");
                }
                break;
            case "TongdaOA_Bypass_Auth":
                uploadInfo.setText("该漏洞不支持文件上传！！！");
                break;
            case "TongdaOA_Delete_Auth":
                uploadInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                uploadInfo.appendText(tongdaOA_deleteAuth.EXP()?"删除auth.inc.php文件成功\n"+"⚠"+url.getText()+"存在通达OAV11.6前台任意文件删除&getshell漏洞\n"+"蚁剑:"+tongdaOA_deleteAuth.getUrl()+tongdaOA_deleteAuth.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OAV11.6前台任意文件删除&getshell漏洞\n");
                break;
            case "TongdaOA_gateway_Upload":
                uploadInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                uploadInfo.appendText(tongdaOA_GatewayUplaod.EXP()?url.getText()+"存在通达OA gateway前台任意文件上传漏洞\n"+"蚁剑:"+tongdaOA_GatewayUplaod.getUrl()+tongdaOA_GatewayUplaod.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OA gateway前台任意文件上传漏洞\n");
                break;
            case "TongdaOA_Api_Ali_Upload":
                uploadInfo.setText("通达OA版本为：" + tongdaOA.getVersion() + "\n");
                uploadInfo.appendText(tongdaOA_apiAliUpload.EXP()?url.getText()+"存在通达OA api_ali前台任意文件上传漏洞\n"+"蚁剑:"+tongdaOA_apiAliUpload.getUrl()+tongdaOA_apiAliUpload.getPath()+"\n"+"密码:x\n":"[-]"+url.getText()+"不存在通达OA api_ali前台任意文件上传漏洞\n");
                break;
        }
    }

    //漏洞列表
    List<String> vulsList = new ArrayList<>();
    //初始化对象
    TongdaOA tongdaOA=new TongdaOA();
    TongdaOA_AnyUserLogin tongdaOA_anyUserLogin = new TongdaOA_AnyUserLogin();
    TongdaOA_ImUpload tongdaOA_imUpload=new TongdaOA_ImUpload();
    TongdaOA_Module_Upload tongdaOA_module_upload = new TongdaOA_Module_Upload();
    TongdaOA_IspiritUpload tongdaOA_ispiritUpload =new TongdaOA_IspiritUpload();
    TongdaOA_IspiritFileInclude tongdaOA_ispiritFileInclude =new TongdaOA_IspiritFileInclude();
    TongdaOA_ActionUpload tongdaOA_actionUpload = new TongdaOA_ActionUpload();
    TongdaOA_AuthmobiLogin tongdaOA_authmobiLogin = new TongdaOA_AuthmobiLogin();
    TongdaOA_Attachment_Remark_FileInclude tongdaOA_attachment_remark_fileInclude = new TongdaOA_Attachment_Remark_FileInclude();
    TongdaOA_ManagementUpload tongdaOA_managementUpload = new TongdaOA_ManagementUpload();
    TongdaOA_BypassAuth tongdaOA_bypassAuth = new TongdaOA_BypassAuth();
    TongdaOA_DeleteAuth tongdaOA_deleteAuth = new TongdaOA_DeleteAuth();
    TongdaOA_gatewayUplaod tongdaOA_GatewayUplaod =new TongdaOA_gatewayUplaod();
    TongdaOA_ApiAliUpload tongdaOA_apiAliUpload = new TongdaOA_ApiAliUpload();

    @FXML
    public void initialize() {
        //为choicebox设置选项列表
        vulsList.add("ALL");
        vulsList.add("TongdaOA_Anyuser_Login");
        vulsList.add("TongdaOA_Im_Upload");
        vulsList.add("TongdaOA_Module_Upload");
        vulsList.add("TongdaOA_Ispirit_Upload");
        vulsList.add("TongdaOA_Ispirit_FileInclude");
        vulsList.add("TongdaOA_Action_Upload");
        vulsList.add("TongdaOA_Authmobi_Login");
        vulsList.add("TongdaOA_Attachement_Remark_FileInclude");
        vulsList.add("TongdaOA_Management_Upload");
        vulsList.add("TongdaOA_Bypass_Auth");
        vulsList.add("TongdaOA_Delete_Auth");
        vulsList.add("TongdaOA_gateway_Upload");
        vulsList.add("TongdaOA_Api_Ali_Upload");
        vuls.getItems().addAll(vulsList);
        //设置choicebox默认选项为ALL
        vuls.setValue("ALL");

        //代理设置窗口
        proxyBtn.setOnAction(event -> {
            //创建一个没有图标的警告对话框
            Alert proxyStage = new Alert(Alert.AlertType.NONE);
            //获取对话框的窗口对象，并设置窗口关闭事件处理
            Window window = proxyStage.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(e->{
                window.hide();//隐藏窗口
            });
            //设置窗口标题
            proxyStage.setTitle("代理设置");
            //创建一个单选按钮组
            ToggleGroup statusGroup = new ToggleGroup();
            //创建启用和禁用两个单选按钮，并将它们加入到单选按钮组中，禁用按钮默认选中
            RadioButton enableRadio = new RadioButton("启用");
            RadioButton disableRadio = new RadioButton("禁用");
            enableRadio.setToggleGroup(statusGroup);
            disableRadio.setToggleGroup(statusGroup);
            disableRadio.setSelected(true);

            //创建一个水平布局，用于放置启用和禁用两个单选按钮
            HBox statusHbox = new HBox();
            statusHbox.setSpacing(10.0);
            statusHbox.getChildren().add(enableRadio);
            statusHbox.getChildren().add(disableRadio);

            // 创建一个网格布局，用于放置代理设置的相关控件，并设置垂直间距
            GridPane proxyGridPane = new GridPane();
            proxyGridPane.setVgap(15.0);
            // 设置网格布局的内边距
            proxyGridPane.setPadding(new Insets(20, 20, 0, 10));

            // 创建代理设置的标签、下拉框、文本框等控件，并初始化默认值
            Label typeLabel = new Label("类型：");
            ComboBox<String> typeCombo = new ComboBox<>();
            typeCombo.setItems(FXCollections.observableArrayList("HTTP", "SOCKS"));
            typeCombo.getSelectionModel().select(0);

            Label IPLabel = new Label("IP地址：");
            TextField IPText = new TextField("127.0.0.1");

            Label PortLabel = new Label("端口：");
            TextField PortText = new TextField("8080");

            Label userNameLabel = new Label("用户名：");
            TextField userNameText = new TextField();

            Label passwordLabel = new Label("密码：");
            TextField passwordText = new TextField();

            // 创建取消和保存按钮
            Button cancelBtn = new Button("取消");
            Button saveBtn = new Button("保存");

            //取消按钮事件
            cancelBtn.setOnAction(e->
                window.hide()
            );

            //保存按钮事件
            saveBtn.setOnAction(e->{
                if(disableRadio.isSelected()){
                    ProxySelector.setDefault(null);
                    proxyStatus.setText("未启用代理");
                    window.hide();
                }else {
                    ProxySelector.setDefault(new MyProxySelector(IPText.getText(),PortText.getText(),typeCombo.getValue()));
                    proxyStatus.setText("代理生效中，"+IPText.getText()+":"+PortText.getText());
                    window.hide();
                }
            });

            proxyGridPane.add(statusHbox, 1, 0);
            proxyGridPane.add(typeLabel, 0, 1);
            proxyGridPane.add(typeCombo, 1, 1);
            proxyGridPane.add(IPLabel, 0, 2);
            proxyGridPane.add(IPText, 1, 2);
            proxyGridPane.add(PortLabel, 0, 3);
            proxyGridPane.add(PortText, 1, 3);
            proxyGridPane.add(userNameLabel, 0, 4);
            proxyGridPane.add(userNameText, 1, 4);
            proxyGridPane.add(passwordLabel, 0, 5);
            proxyGridPane.add(passwordText, 1, 5);
            HBox buttonBox = new HBox();
            buttonBox.setSpacing(20.0);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().add(cancelBtn);
            buttonBox.getChildren().add(saveBtn);
            GridPane.setColumnSpan(buttonBox, 2);
            proxyGridPane.add(buttonBox, 0, 6);
            proxyStage.getDialogPane().setContent(proxyGridPane);
            proxyStage.showAndWait();

        });
    }

    //初始化对象函数
    public void init(){
        tongdaOA.setUrl(url.getText());
        tongdaOA_anyUserLogin.setUrl(url.getText());
        tongdaOA_imUpload.setUrl(url.getText());
        tongdaOA_module_upload.setUrl(url.getText());
        tongdaOA_ispiritUpload.setUrl(url.getText());
        tongdaOA_ispiritFileInclude.setUrl(url.getText());
        tongdaOA_actionUpload.setUrl(url.getText());
        tongdaOA_authmobiLogin.setUrl(url.getText());
        tongdaOA_attachment_remark_fileInclude.setUrl(url.getText());
        tongdaOA_managementUpload.setUrl(url.getText());
        tongdaOA_bypassAuth.setUrl(url.getText());
        tongdaOA_deleteAuth.setUrl(url.getText());
        tongdaOA_GatewayUplaod.setUrl(url.getText());
        tongdaOA_apiAliUpload.setUrl(url.getText());
    }
}
