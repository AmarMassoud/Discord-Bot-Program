package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.exceptions.PermissionException;
import sample.Events.OnReady;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main extends Application implements EventHandler<ActionEvent>, ChangeListener {


    JDA jda;
    Stage window;

    Label welcomeLabel = new Label("Welcome to Discord Bot Program.");
    StackPane changeActivityLayout = new StackPane();
    Label changeActivityLabel = new Label();
    Button changeActivityUpdate = new Button("Update");
    Label changeActivityEmpty = new Label();
    TextField changeActivityText = new TextField();
    String[] activityTypesList = {"Playing", "Watching", "Listening", "Streaming"};
    ChoiceBox<String> activityTypes = new ChoiceBox<String>(FXCollections.observableArrayList(activityTypesList));
    Button backToOptions2 = new Button("Back");
    TextField streamingURL = new TextField();
    Scene changeActivityPage = new Scene(changeActivityLayout, 350, 200);


    StackPane optionsLayout = new StackPane();
    Label optionsLabel = new Label("What do you want to do?");
    Button goServers = new Button("Pick a server");
    Button changeActivity = new Button("Change your bot's activity");
    Button backToToken = new Button("Disconnect");
    Scene optionsPage = new Scene(optionsLayout, 300, 250);

    StackPane tokenPageLayout = new StackPane();   //Just a layout
    Button loginButton = new Button("Login");
    PasswordField tokenInput = new PasswordField();
    Label tokenLabel = new Label("Please begin by entering your bot token.");
    Label tokenLabel2 = new Label();
    Scene tokenPage = new Scene(tokenPageLayout, 400, 350);            // PAGE

    StackPane chooseGuildsLayout = new StackPane();
    ChoiceBox<String> serverList;
    Label serversLabel = new Label("Pick a server");
    Button goServer = new Button("Go");
    Button backToOptions = new Button("Back");
    Scene chooseGuildsScene = new Scene(chooseGuildsLayout, 250, 200);
    Label serversLabel1 = new Label("Please choose a server.");

    StackPane channelsLayout = new StackPane();
    Button sendMessageButton = new Button("Send Message");
    ChoiceBox<String> channelsList;
    Button backToGuild = new Button("Back");
    TextField sendMessage = new TextField();
    HashMap<String, String> channels = new HashMap<>();
    Label sendMessageLabel = new Label();
    Label channelsLabel2 = new Label("Channel:");

    TextField embedTitle = new TextField();
    TextField embedField = new TextField();
    TextField embedColor = new TextField();
    ChoiceBox<String> embedColorChoiceBox = new ChoiceBox<String>();
    EmbedBuilder embed = new EmbedBuilder();
    ChoiceBox<String> messageTypes = new ChoiceBox<String>();
    Button addFieldButton = new Button("Add field");




    ChoiceBox<String> statusTypes = new ChoiceBox();


    Scene chooseChannelScene = new Scene(channelsLayout, 500, 300);





    HashMap<String, String> guilds = new HashMap<String, String>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        window = primaryStage;
        primaryStage.setTitle("Discord Bot Program");
        window.setResizable(false);


        tokenPageLayout.getChildren().add(welcomeLabel);
        welcomeLabel.setFont(javafx.scene.text.Font.font(Font.BOLD));
        welcomeLabel.setTranslateY(-100);
        welcomeLabel.setId("welcomeLabel");
        tokenLabel.setId("tokenLabel");

        changeActivityLayout.getChildren().add(changeActivityLabel);
        changeActivityLayout.getChildren().add(changeActivityText);
        changeActivityLayout.getChildren().add(changeActivityEmpty);
        changeActivityLayout.getChildren().add(changeActivityUpdate);
        changeActivityLayout.getChildren().add(activityTypes);
        changeActivityLayout.getChildren().add(backToOptions2);
        changeActivityLayout.getChildren().add(streamingURL);
        changeActivityLayout.getChildren().add(statusTypes);
        streamingURL.setTranslateY(30);
        streamingURL.setTranslateX(45);
        streamingURL.setPromptText("Stream URL");
        streamingURL.setMaxWidth(200);
        streamingURL.setVisible(false);
        changeActivityEmpty.setVisible(false);
        changeActivityEmpty.setTranslateY(70);
        changeActivityLabel.setScaleX(1.5);
        changeActivityLabel.setScaleY(1.5);
        changeActivityUpdate.setTranslateY(30);
        changeActivityUpdate.setMaxWidth(85);
        changeActivityUpdate.setTranslateX(0);
        changeActivityText.setPromptText("Activity");
        changeActivityText.setMaxWidth(200);
        changeActivityText.setTranslateX(45);
        activityTypes.setTranslateX(-100);
        activityTypes.setMaxWidth(85);
        statusTypes.setTranslateX(-100);
        statusTypes.setTranslateY(30);
        statusTypes.setValue("Online");
        statusTypes.setMaxWidth(85);

        activityTypes.getSelectionModel().selectedItemProperty().addListener(this);
        changeActivityLabel.setTranslateY(-55);
        backToOptions2.setTranslateY(70);
        backToOptions2.setTranslateX(125);
        backToOptions2.setOnAction(event -> {
            window.setScene(optionsPage);
            changeActivityEmpty.setVisible(false);
            changeActivityText.setText("");
            activityTypes.setValue("");
        });

        chooseGuildsLayout.getChildren().add(serversLabel);
        chooseGuildsLayout.getChildren().add(goServer);
        chooseGuildsLayout.getChildren().add(backToOptions);
        chooseGuildsLayout.getChildren().add(serversLabel1);
        serversLabel1.setTranslateY(65);
        serversLabel1.setTranslateX(-23);
        serversLabel1.setVisible(false);
        chooseGuildsLayout.setTranslateY(-25);
        goServer.setTranslateX(65);
        goServer.setTranslateY(25);
        serversLabel.setTranslateY(-35);
        serversLabel.setScaleX(2.5);
        serversLabel.setScaleY(2.5);
        backToOptions.setTranslateY(65);
        backToOptions.setTranslateX(65);
        backToOptions.setOnAction(event -> {

            window.setScene(optionsPage);
        });


        optionsLayout.getChildren().add(optionsLabel);
        optionsLayout.getChildren().add(backToToken);
        optionsLayout.getChildren().add(changeActivity);
        optionsLayout.getChildren().add(goServers);
        optionsLabel.setTranslateY(-50);
        changeActivity.setTranslateY(50);
        backToToken.setTranslateY(100);
        backToToken.setTranslateX(100);
        changeActivity.setOnAction(event -> {
            try {
                changeActivityText.setText(jda.getPresence().getActivity().getName());
                switch (jda.getPresence().getActivity().getType()){
                    case WATCHING:
                        activityTypes.setValue("Watching");
                        break;
                    case LISTENING:
                        activityTypes.setValue("Listening");
                        break;
                    case STREAMING:
                        activityTypes.setValue("Streaming");
                        break;
                    case DEFAULT:
                        if (!jda.getPresence().getActivity().getName().isEmpty() || !jda.getPresence().getActivity().getName().equalsIgnoreCase(" ")) {
                            activityTypes.setValue("Playing");
                        } else{
                            activityTypes.setValue("");
                        }
                        break;
                    case CUSTOM_STATUS:
                    default:
                        activityTypes.setValue("");
                        break;
                }
            } catch (Exception ignored){
            }
            window.setScene(changeActivityPage);
            changeActivityLabel.setText(jda.getSelfUser().getName() + "'s Activity:");
        });
        changeActivityUpdate.setOnAction(event -> {
            if(changeActivityText.getText().isEmpty() || changeActivityText.getText().equalsIgnoreCase("")) {
                changeActivityEmpty.setText("The text field shouldn't be empty.");
                changeActivityEmpty.setVisible(true);

            }
            else if(activityTypes.getSelectionModel().getSelectedIndex() == 3){
                if(Activity.isValidStreamingUrl(streamingURL.getText())){
                    jda.getPresence().setActivity(Activity.streaming(changeActivityText.getText(), streamingURL.getText()));
                    changeActivityLabel.setVisible(true);
                    changeActivityEmpty.setVisible(false);
                }else{
                    changeActivityEmpty.setText("Invalid streaming URL.");
                    changeActivityEmpty.setVisible(true);
                }

            }
            else {
                try{
                    Activity activity;
                    switch (activityTypes.getValue()) {
                        case "Watching":
                            activity = Activity.watching(changeActivityText.getText());
                            break;
                        case "Listening":
                            activity = Activity.listening(changeActivityText.getText());
                            break;
                        default:
                            activity = Activity.playing(changeActivityText.getText());
                    }
                    jda.getPresence().setActivity(activity);
                    changeActivityEmpty.setText("Activity changed successfully.");
                    changeActivityEmpty.setVisible(true);
                }catch (NullPointerException e){
                    changeActivityEmpty.setText("Choose an activity type.");
                    changeActivityEmpty.setVisible(true);
                }
            }
            switch (statusTypes.getValue()) {
                case "Online":
                    jda.getPresence().setStatus(OnlineStatus.ONLINE);
                    break;
                case "AFK":
                    jda.getPresence().setStatus(OnlineStatus.IDLE);
                    break;
                case "Do Not Disturb":
                    jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
                    break;
                case "Offline":
                    jda.getPresence().setStatus(OnlineStatus.INVISIBLE);
                    break;
            }
        });

        goServers.setOnAction(this);
        goServer.setOnAction(this);
        backToToken.setOnAction(event -> { //onAction
            try {
                tokenLabel2.setVisible(false);
                jda.shutdownNow();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            window.setScene(tokenPage);
        });

        tokenPageLayout.getChildren().add(loginButton);
        tokenPageLayout.getChildren().add(tokenInput);
        tokenPageLayout.getChildren().add(tokenLabel);
        tokenPageLayout.getChildren().add(tokenLabel2);
        tokenLabel2.setVisible(false);
        tokenLabel2.setTranslateY(75);
        tokenLabel.setTranslateY(-50);
        loginButton.setTranslateY(50);
        loginButton.setOnAction(this);

        channelsLayout.getChildren().add(sendMessageButton);
        channelsLayout.getChildren().add(sendMessage);
        channelsLayout.getChildren().add(backToGuild);
        channelsLayout.getChildren().add(sendMessageLabel);
        channelsLayout.getChildren().add(channelsLabel2);

        channelsLabel2.setTranslateY(-136);
        channelsLabel2.setTranslateX(-220);
        backToGuild.setTranslateY(-135);
        backToGuild.setTranslateX(200);
        backToGuild.setMinWidth(90);
        sendMessage.setMaxWidth(390);
        sendMessage.setTranslateY(135);
        sendMessage.setTranslateX(-50);
        sendMessage.setMaxHeight(25);
        sendMessageButton.setTranslateY(135);
        sendMessageButton.setTranslateX(196);
        sendMessageButton.setMaxWidth(95);



        embedTitle.setVisible(false);
        embedTitle.setTranslateX(-50);
        embedTitle.setTranslateY(40);
        embedTitle.setMinWidth(90);
        embedTitle.setMaxWidth(390);
        embedTitle.setPromptText("Title");
        embedField.setVisible(false);
        embedField.setTranslateX(-50);
        embedField.setTranslateY(70);
        embedField.setMinWidth(90);
        embedField.setMaxWidth(390);
        embedField.setPromptText("Main text");
        embedColor.setVisible(false);
        embedColor.setTranslateX(-50);
        embedColor.setTranslateY(100);
        embedColor.setMinWidth(90);
        embedColor.setMaxWidth(390);
        embedColor.setText("0x000000");
        embedColorChoiceBox.setValue("Color code");
        embedColorChoiceBox.getItems().add("Color code");
        embedColorChoiceBox.getItems().add("Black");
        embedColorChoiceBox.getItems().add("Gray");
        embedColorChoiceBox.getItems().add("White");
        embedColorChoiceBox.getItems().add("Purple");
        embedColorChoiceBox.getItems().add("Blue");
        embedColorChoiceBox.getItems().add("Cyan");
        embedColorChoiceBox.getItems().add("Green");
        embedColorChoiceBox.getItems().add("Yellow");
        embedColorChoiceBox.getItems().add("Orange");
        embedColorChoiceBox.getItems().add("Red");
        embedColorChoiceBox.setTranslateY(100);
        embedColorChoiceBox.setTranslateX(196);
        embedColorChoiceBox.setMaxWidth(95);
        embedColorChoiceBox.getSelectionModel().selectedItemProperty().addListener(this);
        addFieldButton.setTranslateX(196);
        addFieldButton.setTranslateY(40);
        addFieldButton.setMaxWidth(95);
        addFieldButton.setMinWidth(95);
        addFieldButton.setVisible(false);
        channelsLayout.getChildren().add(embedColorChoiceBox);
        channelsLayout.getChildren().add(embedTitle);
        channelsLayout.getChildren().add(embedField);
        channelsLayout.getChildren().add(messageTypes);
        channelsLayout.getChildren().add(embedColor);
        channelsLayout.getChildren().add(addFieldButton);

        messageTypes.getItems().add("Embed");
        messageTypes.getItems().add("Message");
        messageTypes.getItems().add("TTS");
        messageTypes.getSelectionModel().selectedItemProperty().addListener(this);
        messageTypes.setTranslateY(70);
        messageTypes.setTranslateX(196);
        messageTypes.setMaxWidth(95);

        addFieldButton.setOnAction(event -> {


            if(embedTitle.getText().isEmpty() && embedField.getText().isEmpty()){

                sendMessageLabel.setText("Added an empty field.");
                sendMessageLabel.setVisible(true);
                embed.addField(embedTitle.getText(), embedField.getText(), true);

            } else if(!embedTitle.getText().isEmpty() && embedField.getText().isEmpty()){
                sendMessageLabel.setText("Added the title.");
                sendMessageLabel.setVisible(true);
                embed.addField(  embedTitle.getText() , embedField.getText(), true);
            } else {
                sendMessageLabel.setText("Added the field.");
                sendMessageLabel.setVisible(true);
                embed.addField(embedTitle.getText(), embedField.getText(), false);
            }
            embedTitle.setText("");
            embedField.setText("");
        });

        sendMessageButton.setOnAction(e -> {

            if(jda.getGuildById(guilds.get(serverList.getValue())).getSelfMember().hasPermission(Permission.VIEW_CHANNEL) && (jda.getGuildById(guilds.get(serverList.getValue())).getSelfMember().hasPermission(Permission.MESSAGE_WRITE))) {
                try {
                    if(messageTypes.getValue().equals("Message")) {
                        if(sendMessage.getText().length() < 2001) {
                            jda.getTextChannelById(channels.get(channelsList.getValue())).sendMessage(sendMessage.getText()).queue();
                        } else  {
                            sendMessageLabel.setText("The message can't be longer than 2000 characters.");
                            sendMessageLabel.setVisible(true);
                        }
                        sendMessageLabel.setVisible(false);
                        sendMessage.setText("");
                    } else if(messageTypes.getValue().equals("Embed")) {
                        try {
                            sendMessageLabel.setText("");
                            int color = 0;
                            switch (embedColorChoiceBox.getValue()){
                                case "Black":
                                    color = Color.black.getRGB();
                                    break;
                                case "White":
                                    color = 0xffffff;
                                    break;
                                case "Gray":
                                    color = Color.gray.getRGB();
                                    break;
                                case "Purple":
                                    color = 0x840bdb;
                                    break;
                                case "Blue":
                                    color = Color.blue.getRGB();
                                    break;
                                case "Cyan":
                                    color = Color.cyan.getRGB();
                                    break;
                                case "Green":
                                    color = Color.green.getRGB();
                                    break;
                                case "Yellow":
                                    color = Color.yellow.getRGB();
                                    break;
                                case "Orange":
                                    color = 0xdb5a0b;
                                    break;
                                case "Red":
                                    color = Color.red.getRGB();
                                    break;
                                default:
                                    break;
                            }
                            if(embed.isEmpty()){
                                sendMessageLabel.setText("There are no fields in this embed.");
                            } else {

                                if(color != 0){
                                    embed.setColor(color);
                                }else {
                                    embed.setColor(Color.decode(embedColor.getText()));
                                }

                                jda.getTextChannelById(channels.get(channelsList.getValue())).sendMessage(embed.build()).queue();
                                embed.clear();
                                embedField.setText("");
                                embedTitle.setText("");
                            }
                        } catch(NumberFormatException eee) {
                            sendMessageLabel.setText("Please specify a valid color code.");
                        }

                    } else if(messageTypes.getValue().equals("TTS")){
                        MessageBuilder sendMessageText = new MessageBuilder();
                        sendMessageText.setContent(sendMessage.getText());
                        sendMessageText.setTTS(true);
                        jda.getTextChannelById(channels.get(channelsList.getValue())).sendMessage(sendMessageText.build()).queue();
                        sendMessage.setText("");
                    }
                } catch(IllegalArgumentException exc) {
                    if (sendMessage.getText().isEmpty() || sendMessage.getText().equalsIgnoreCase("")) {
                        try{
                            channelsList.getValue().isEmpty();
                            sendMessageLabel.setText("Please specify a message.");
                            sendMessageLabel.setVisible(true);
                        }catch (Exception e1){
                            sendMessageLabel.setText("Please specify a channel.");
                            sendMessageLabel.setVisible(true);
                        }

                    } else {
                        sendMessageLabel.setText("Please specify a channel.");
                        sendMessageLabel.setVisible(true);
                    }
                } catch (InsufficientPermissionException exc) {
                    sendMessageLabel.setText("The bot does not have permission to send this message.");
                } catch (PermissionException exc) {
                    sendMessageLabel.setVisible(true);
                    sendMessageLabel.setText("The bot does not have permission to use this feature.");

                }
            }

        });
        backToGuild.setOnAction(event -> {
            window.setScene(chooseGuildsScene);
        });




        sendMessage.setPromptText("Input a message to send to the channel");
        embedTitle.setPromptText("Title");
        embedField.setPromptText("Main text");


        statusTypes.getItems().add("Online");
        statusTypes.getItems().add("AFK");
        statusTypes.getItems().add("Do Not Disturb");
        statusTypes.getItems().add("Offline");


        tokenPage.getStylesheets().add("sample/file.css");
        changeActivityPage.getStylesheets().add("sample/file.css");
        optionsPage.getStylesheets().add("sample/file.css");
        chooseGuildsScene.getStylesheets().add("sample/file.css");
        chooseChannelScene.getStylesheets().add("sample/file.css");

        primaryStage.setScene(tokenPage);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent e) {
        if(e.getSource() == loginButton){
            try {
                jda = JDABuilder.createDefault(tokenInput.getText().trim()).build();
                jda.addEventListener(new OnReady());
                window.setScene(optionsPage);



            } catch (Exception ex) {
                tokenLabel2.setText("Invalid token or bad connection. Try again.");
                tokenLabel2.setVisible(true);
                System.out.println("Failed to login to the bot.");
            }
        }  else if (e.getSource() == goServers) {
            try {
                TimeUnit.MILLISECONDS.sleep(1700);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(jda.getGuilds().isEmpty()){
                goServers.setText("0 servers found.");

            } else {
                goServers.setText("Pick a server");
                pickGuild();
// amar said hi
            }
        } else if(e.getSource() == goServer) {
            try {
                serverList.getValue().isEmpty();
                messageTypes.setValue("Message");
                window.setScene(chooseChannelScene);
                pickChannel();
                serversLabel1.setVisible(false);
            } catch (Exception e1) {

                serversLabel1.setVisible(true);
            }
        }

    }
    public void pickGuild(){

        guilds.clear();
        for (Guild guild : jda.getGuilds()) {
            guilds.put(guild.getName(), guild.getId());
        }
        serverList = new ChoiceBox<>(FXCollections.observableArrayList(guilds.keySet()));
        chooseGuildsLayout.getChildren().add(serverList);
        serverList.setTranslateY(20);
        serverList.setTranslateX(-15);
        serverList.setMaxWidth(100);
        serverList.setMinWidth(100);
        window.setScene(chooseGuildsScene);
    }
    public void pickChannel() {

        channels.clear();
        String myServer = serverList.getValue();
        for (Guild guilds : jda.getGuilds()) {
            if (guilds.getName().equals(myServer)) {
                for (TextChannel channel : guilds.getTextChannels()) {
                    if(guilds.getSelfMember().hasPermission(channel, Permission.VIEW_CHANNEL)) {
                        channels.put(channel.getName(), channel.getId());
                    }
                }
                channelsList = new ChoiceBox<>(FXCollections.observableArrayList(channels.keySet()));
                channelsList.setTranslateX(-85);
                channelsList.setMinWidth(200);
                channelsList.setTranslateY(-135);
                channelsLayout.getChildren().add(channelsList);
            }
        }
    }

    public void setJda(JDA jda){
        this.jda = jda;
    }


    @Override
    public void stop(){
        try{
            jda.shutdownNow();
            tokenInput.setText("");
        } catch (Exception ignored){}
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {

        if(newValue.equals("Streaming")){
            streamingURL.setVisible(true);
            changeActivityUpdate.setTranslateY(60);
            changeActivityEmpty.setTranslateY(80);
        }else if(newValue.equals("Watching") || newValue.equals("Playing") || newValue.equals("Listening")){
            streamingURL.setVisible(false);
            changeActivityUpdate.setTranslateY(30);
            changeActivityEmpty.setTranslateY(70);
        }

        if(newValue.equals("Color code") && messageTypes.getValue().equalsIgnoreCase("embed")){
            embedColor.setVisible(true);
        }
        else if(newValue.equals("Embed")) {
            sendMessageButton.setText("Send Embed");
            addFieldButton.setVisible(true);
            embedColorChoiceBox.setVisible(true);
            embedTitle.setVisible(true);
            embedField.setVisible(true);
            sendMessage.setVisible(false);
            embedTitle.setPromptText("Title");
            embedField.setPromptText("Main text");
            embedColor.setPromptText("Color code (e.g. 0x000000)");
            if(embedColorChoiceBox.getValue().equalsIgnoreCase("Color code")){
                embedColor.setVisible(true);
            }
        } else if(newValue.equals("TTS") || newValue.equals("Message")){
            sendMessageButton.setText("Send Message");
            addFieldButton.setVisible(false);
            embedColorChoiceBox.setVisible(false);
            embedTitle.setVisible(false);
            embedField.setVisible(false);
            embedColor.setVisible(false);
            sendMessage.setVisible(true);
            sendMessage.setPromptText("Input a message to send to the channel");

        } else {
            embedColor.setVisible(false);
        }
    }


}
