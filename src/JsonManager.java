import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonManager {
    File file = new File("timings.json");
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    List<JsonData> jsonDataList = new ArrayList<>();
    int butName;
    static String importRollback;

    public void jsonReader() {
        try {
            Object obj = new JSONParser().parse(new FileReader("timings.json"));
            JSONArray jsonArray = (JSONArray) obj;
            JSONObject jsonObject = (JSONObject) jsonArray.get(butName);
            String date = (String) jsonObject.get("date");

            if (Objects.equals(date, "none")){
                new TimeMath().timeMath();
                jsonReplace();
            }
            else {
                System.out.println("Значение уже существует. Хотите перезаписать?");
                Frame.modalReplace.setVisible(true);
            }

        } catch (IOException e) {
            System.out.println("[ERROR] Не удалось прочитать файл. Код ошибки: ");
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("[ERROR] Не удалось прочитать значение файла. Код ошибки: ");
            throw new RuntimeException(e);
        }
    }
    public void jsonWriter() {
        if (file.exists() && !file.isDirectory()) {
            System.out.println("Файл найден.");
            return;
        }
        else
            System.out.println("Файл не найден.");
        for (int i = 0; i < 30; ++i) {
            jsonDataList.add(new JsonData(Frame.butName[i], "test", "none", "none"));
        }

        try {
            FileWriter writer = new FileWriter("timings.json");
            writer.write(gson.toJson(jsonDataList));
            writer.close();
        } catch (IOException e) {
            System.out.println("[ERROR] Не удалось записать файл. Код ошибки: ");
            throw new RuntimeException(e);
        }
    }
    public void jsonReplace() {
        try {
            Object obj = new JSONParser().parse(new FileReader("timings.json"));
            JSONArray jsonArray = (JSONArray) obj;
            JSONObject jsonObject = (JSONObject) jsonArray.get(butName);

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter simpleDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dateOutSoloWrite = simpleDate.format(date);

            jsonObject.replace("date", dateOutSoloWrite);
            jsonObject.replace("rollback", TimeMath.getDateT2());
            FileWriter writer = new FileWriter("timings.json");
            writer.write(gson.toJson(obj));
            writer.close();
        } catch (IOException e) {
            System.out.println("[ERROR] Не удалось прочитать/записать файл. Код ошибки: ");
            throw new RuntimeException(e);
        } catch (ParseException e) {
            System.out.println("[ERROR] Не удалось получить значение файла. Код ошибки: ");
            throw new RuntimeException(e);
        }
        System.out.println("Значение обновлено успешно!");
        Frame.modalReplace.setVisible(false);
    }
    public void jsonChecker(int value) {
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader("timings.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObject = (JSONObject) jsonArray.get(value);
        String objectJson = (String) jsonObject.get("date");
        if (!Objects.equals(objectJson, "none")) {
            importRollback = (String) jsonObject.get("rollback");
        }
        else {
            importRollback = null;
        }
    }
    public void jsonTimeout(){
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader("timings.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = (JSONArray) obj;
        for(int i = 0; i < 30; i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String rollback = (String) jsonObject.get("rollback");
            if (!Objects.equals(rollback, "none")){
                System.out.println("[" + i + "]" + "Значения не пусты");
                long dif = new TimeMath().timeMath2(rollback);
                System.out.println(dif);
                if (dif <= 0){
                    jsonObject.put("date", "none");
                    jsonObject.put("rollback", "none");
                    System.out.println("Значение выпилено");
                    new JsonManager().jsonClear(i);
                }
            }
        }
    }
    public void jsonClear(int i){
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader("timings.json"));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = (JSONArray) obj;
        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

        jsonObject.replace("date", "none");
        jsonObject.replace("rollback", "none");

        FileWriter writer;
        try {
            writer = new FileWriter("timings.json");
            writer.write(gson.toJson(obj));
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setClick(int butName) {
        this.butName = butName;
        jsonReader();
    }

    public static String getImportRollback() {
        return importRollback;
    }
}