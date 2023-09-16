# 通达OA漏洞利用工具

**免责声明：请勿从事非法测试，利用此工具而造成的任何直接或者间接的后果及损失，均由使用者本人负责，所产生的一切不良后果与作者无关。该仅供安全人员用于授权测试，请勿非法使用！！！**

**本工具webshell采用蚁剑连接，密码均为`x`**

**注意：通达OA v11.6前台任意文件删除+任意文件上传漏洞会删除auth.inc.php，这可能会损坏OA系统谨慎操作**

**TongdaOATool本工具支持以下漏洞的验证和利用：**

```
	1、通达OA任意用户登录漏洞（TongdaOA_AnyUser_Login）
		影响版本：2017-V11.4
	2、通达OA后台im任意文件上传漏洞（Tongda_Im_Upload）
		影响版本：2017-V11.4
   	3、通达OA后台module任意文件上传漏洞（Tongda_Module_Upload）
        	影响版本：2017-V11.4
	4、通达OA后台ispirit任意文件上传漏洞（Tongda_Ispirit_Upload）
		影响版本：2017-V11.4
	5、通达OA前台ispirit文件包含漏洞（Tongda_Ispirit_FileInclude）
		影响版本：v11-v11.3
	6、通达OA前台action任意文件上传漏洞（Tongda_Action_Upload）
		影响版本：2016-V11.6
	7、通达OA前台authmobi伪造在线用户登录漏洞（TongdaOA_Authmobi_Login）
		影响版本：2017-V11.7
 	8、通达OA后台attachment_remark文件包含漏洞（TongdaOA_Attachment_remark_FileInclude）
		影响版本：2017-V11.7
	9、通达OA后台management任意文件上传漏洞（TongdaOA_Management_Upload）
		影响版本：2017-V11.6
	10、通达OA登录认证绕过漏洞(TongdaOA_Bypass_Auth)
		影响版本：通达OA2013、通达OA2016、通达OA2017
	11、通达OA v11.6前台任意文件删除+任意文件上传漏洞(TongdaOA_Delete_Auth)
		影响版本：v11.6
	12、通达OA gataway前台任意文件上传漏洞
		影响版本：v11.8-v11.10
	13、通达OA api_ali前台任意文件上传漏洞
		影响版本：v11.2-v11.9
```
后续待更新其他漏洞，欢迎各位大佬提bug。
# 更新记录
```
V1.1
	1、修改webshell为免杀常见杀软的webshell
	2、更换webshell工具冰蝎为蚁剑
	3、新增通达OA后台module任意文件上传
V1.2
	新增一下漏洞检测
	1.新增通达OA前台action任意文件上传漏洞
	2.新增通达OA前台ispirit文件包含漏洞
	3.新增通达OA后台attachment_remark文件包含漏洞
	4.新增通达OA后台management任意文件上传漏洞
	5.新增通达OA v11.6前台任意文件删除+任意文件上传漏洞
	6.新增通达OA v11.8 前台任意文件上传漏洞
V1.3
	1. 代码开源
	2. 增加http、socks代理功能，方便抓包分析或内网环境下使用
	3. 采用线程池修复卡顿问题
	4. 新增通达OA前台authmobi伪造在线用户登录漏洞（TongdaOA_Authmobi_Login）
	5. 新增通达OA登录认证绕过漏洞(TongdaOA_Bypass_Auth)
	6. 新增通达OA gataway前台任意文件上传漏洞
```

增加http、socks代理功能，方便抓包分析代码或内网环境下使用

<img width="692" alt="image" src="https://github.com/xiaokp7/TongdaOATool/assets/105373673/a107fa95-a230-4c78-bb5b-382b6a496237">

<img width="1621" alt="image" src="https://github.com/xiaokp7/TongdaOATool/assets/105373673/6fcd4905-491f-49bf-bee7-aa42ce38fe57">

采用线程池解决检测漏洞时卡顿问题

<img width="695" alt="image" src="https://github.com/xiaokp7/TongdaOATools/assets/105373673/2b9307bb-1d78-470b-9120-41dff5a24d7f">

<img width="695" alt="image" src="https://github.com/xiaokp7/TongdaOATools/assets/105373673/87e52f99-203e-436f-96ba-850eb7fe7f9f">

<img width="695" alt="image" src="https://github.com/xiaokp7/TongdaOATools/assets/105373673/a3a4b809-ddd1-45af-94b2-ab17ab8cad10">

<img width="788" alt="image" src="https://github.com/xiaokp7/TongdaOATools/assets/105373673/12b511c5-f6e4-4854-8cdc-28fea0a8b025">











