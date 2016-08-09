package resourcepool.io.handson_android_app.model;

/**
 * Created by loicortola on 08/08/2016.
 */
public class Message {
    public final String author;
    public final String message;

    private static final String[] NAMES = new String[]{"Jacques Pearsall", "Kathrine Dahlin", "Jody Circle", "Christen Sturgis",
            "Mervin Schrimsher", "Genevive Brumback", "Gaston Emrich", "Nakisha Ahmad", "June Hardwick", "Hollie Barker", "Ethan Onofrio", "Caterina Membreno", "Retta Pelley", "Malisa Tharp", "Margot Filiault", "Dominic Mcgee", "Caron Dieckman", "Janay Cowie", "Bret Schenck", "Lavonda Pardon", "Nora Twigg", "Ardith Valiente", "Allan Obrian", "Lilia Fuerst", "Jalisa Bouie", "Kyla Weathers", "Delsie Ayala", "Shanell Riggins", "Raven Lan", "Catina Geronimo", "Vashti Wigfall", "Jadwiga Marchi", "Ona Merlos", "Celena Seaberg", "Lawrence Swader", "Enedina Shanklin", "Maria Stansel", "Sheilah Batman", "Luther Fischetti", "Kurt Shafer", "Lance Baltazar", "Birdie Maffucci", "Lorine Thode", "Magdalene Mcintosh", "Amelia Alfonso", "Verla Boldt", "Tarra Pugliese", "Issac Kettner", "Daniela Reep", "Maisha Pavelka"
    };

    public Message(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public static Message random() {
        return new Message(NAMES[(int) (Math.random() * NAMES.length)], "Hello world !");
    }
}
