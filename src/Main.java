import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //创建根节点，导入fxml样式文件
        Parent root = FXMLLoader.load(getClass().getResource("ui/Main.fxml"));
        //创建场景，添加根节点
        Scene scene = new Scene(root);
        //向舞台中添加场景
        primaryStage.setScene(scene);
        //设置舞台标题
        primaryStage.setTitle("TongdaOATool v1.3 by xiaokp7");
        //展示舞台
        primaryStage.show();
    }
}
