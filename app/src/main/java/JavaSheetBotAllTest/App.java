
package JavaSheetBotAllTest;

import SheetApi.GoogleSheetsAli;
import Telegram.BotBuilder;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App extends TelegramLongPollingBot{
    
    private static List<List<Object>> values;
    private static GoogleSheetsAli api;
    
    public static void main(String[] args)  throws TelegramApiRequestException, TelegramApiException {
        App bot = new App();
        TelegramBotsApi botApi = new TelegramBotsApi(DefaultBotSession.class);
        botApi.registerBot(bot);
        Thread thread1 = new Thread(() ->{
            while(true){
                api = new GoogleSheetsAli();
                values =  api.values("main").getValues();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        System.out.println("Bot started!");
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        Thread thread = new Thread(() -> {
            
            BotBuilder handler = new BotBuilder(this);
            if(update.hasMessage()){
                // message
                String message = update.getMessage().getText();
                for(int i = 0; i < values.size(); i++){
                    String key = values.get(i).get(1).toString();
                    if(message.equals(key)){
                        handler.sendMsg(update.getMessage().getChatId().toString(), "Time: " + values.get(i).get(8).toString());
                        break;
                    }
                }
            }else if(update.hasCallbackQuery()){
                //callback
                
            }
        });
        thread.start();
    }
     

    @Override
    public String getBotUsername() {
        return "@Create_javabot_bot";
    }
    
    @Override
    public String getBotToken() {
        return "5158467150:AAFN538juEp1_ELOWf_ZTlfLKz_Ovz3r7n8";
    }

}
