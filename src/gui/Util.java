package gui;

import java.util.Random;

/**
 * Created by Kyle on 5/19/2016.
 */
public class Util {
    public static final Random RAND = new Random();

    public static org.newdawn.slick.Color slickColor(Color color) {
        return new org.newdawn.slick.Color(color.r, color.g, color.b, color.a);
    }



//    private static void createCardCodeDict() {
//        try {
//            File cardDataFile = new File("Netrunner Draft/res/cardData.txt");
//            InputStream inputStream = new FileInputStream(cardDataFile);
//            JsonReader jsonReader = Json.createReader(inputStream);
//            JsonArray cardData = jsonReader.readArray();
//            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
//            for (JsonObject card : cardData.getValuesAs(JsonObject.class)) {
//                String title = card.get("title").toString();
//                title = title.substring(1, title.length() - 1);
//                String code = card.get("code").toString();
//                code = code.substring(1, code.length() - 1);
//                objectBuilder.add(title, code);
//                objectBuilder.add(code, title);
//            }
//            JsonObject object = objectBuilder.build();
//            JsonWriter writer = Json.createWriter(new FileOutputStream("Netrunner Draft/res/cardCodeDict.txt"));
//            writer.writeObject(object);
//        } catch (Exception e) { e.printStackTrace(); }
}
