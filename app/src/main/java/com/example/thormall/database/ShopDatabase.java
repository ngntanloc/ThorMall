package com.example.thormall.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.thormall.model.CartItem;
import com.example.thormall.model.GroceryItem;

import java.util.ArrayList;

@Database(entities = {GroceryItem.class, CartItem.class}, version = 1)
public abstract class ShopDatabase extends RoomDatabase {

    public abstract GroceryItemDao groceryItemDao();
    public abstract CartItemDao cartItemDao();

    private static ShopDatabase instance;

    public static synchronized ShopDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, ShopDatabase.class, "shop_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(initialCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback initialCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialAsyncTask(instance).execute();
        }
    };

    private static class InitialAsyncTask extends AsyncTask<Void, Void, Void> {

        private GroceryItemDao groceryItemDao;

        public InitialAsyncTask(ShopDatabase db) {
            this.groceryItemDao = db.groceryItemDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<GroceryItem> allItems = new ArrayList<>();

            allItems.add(new GroceryItem("Ice Cream", "Ice cream (derived from earlier iced cream or cream ice) is a sweetened frozen food typically eaten as a snack or dessert. It may be made from dairy milk or cream and is flavoured with a sweetener, either sugar or an alternative, and any spice, such as cocoa or vanilla.",
                    "https://ipcdn.freshop.com/resize?url=https://images.freshop.com/00011161451013/247b1a393d4f8fd7ba5dc55a7906aa7b_large.png&width=512&type=webp&quality=80",
                    "Food", 5.4, 10));

            GroceryItem milk = new GroceryItem("Milk", "Milk is a nutrient-rich, white liquid food produced by the mammary glands of mammals. It is the primary source of nutrition for infant mammals before they are able to digest other types of food.",
                    "https://www.psdmockups.com/wp-content/uploads/2019/06/1L-Tetra-Pak-Carton-Boxes-PSD-Mockup.jpg",
                    "Drink", 2.3, 8);
            allItems.add(milk);

            GroceryItem soda = new GroceryItem("Soda", "A soft drink is a drink that usually contains carbonated water, a sweetener, and a natural or artificial flavoring. The sweetener may be a sugar, high-fructose corn syrup, fruit juice, a sugar substitute, or some combination of these",
                    "https://cdn.diffords.com/contrib/bws/2019/05/5cc9b8261f976.jpg",
                    "Drink", 0.99, 15);
            allItems.add(soda);

            GroceryItem shampoo = new GroceryItem("Shampoo", "Shampoo is a hair care product, typically in the form of a viscous liquid, that is used for cleaning hair. Less commonly, shampoo is available in bar form, like a bar of soap. Shampoo is used by applying it to wet hair, massaging the product into the scalp, and then rinsing it out. Some users may follow a shampooing with the use of hair conditioner.",
                    "https://res.cloudinary.com/mtree/image/upload/q_auto,f_auto/HeadandShoulders_PH_MW/9Gq7gblVJdM5RfPkfdp5H/6302bb00431710a9b9abf450a31b73e3/HS_PH_Menthol_Large.jpg",
                    "Cleanser", 14.5, 9);
            allItems.add(shampoo);

            GroceryItem spaghetti = new GroceryItem("Spaghetti",
                    "Spaghetti is a long, thin, solid, cylindrical pasta. It is a staple food of traditional Italian cuisine. Like other pasta, spaghetti is made of milled wheat and water and sometimes enriched with vitamins and minerals. Italian spaghetti is typically made from durum wheat semolina.",
                    "https://sc01.alicdn.com/kf/UTB8AoDnIJoSdeJk43Owq6ya4XXak.jpg_350x350.jpg",
                    "Food", 3.85, 6);

            allItems.add(spaghetti);

            GroceryItem soap = new GroceryItem("Soap", "Soap is a salt of a fatty acid[1] used in a variety of cleansing and lubricating products. In a domestic setting, soaps are surfactants usually used for washing, bathing, and other types of housekeeping. In industrial settings, soaps are used as thickeners, components of some lubricants, and precursors to catalysts.",
                    "https://www.londondrugs.com/on/demandware.static/-/Sites-londondrugs-master/default/dwfcbde309/products/L9276163/large/L9276163.JPG",
                    "Cleanser", 2.65, 14);
            allItems.add(soap);

            GroceryItem juice = new GroceryItem("Juice", "Juice is a drink made from the extraction or pressing of the natural liquid contained in fruit and vegetables. It can also refer to liquids that are flavored with concentrate or other biological food sources, such as meat or seafood, such as clam juice. Juice is commonly consumed as a beverage or used as an ingredient or flavoring in foods or other beverages, as for smoothies. Juice emerged as a popular beverage choice after the development of pasteurization methods enabled its preservation without using fermentation (which is used in wine production)",
                    "https://dg6qn11ynnp6a.cloudfront.net/wp-content/uploads/2015/04/199373.jpg",
                    "Drink", 3.45, 25);
            allItems.add(juice);

            GroceryItem walnut = new GroceryItem("Walnut", "A walnut is the nut of any tree of the genus Juglans (Family Juglandaceae), particularly the Persian or English walnut, Juglans regia. A walnut is the edible seed of a drupe, and thus not a true botanical nut. It is commonly consumed as a nut.",
                    "https://sc01.alicdn.com/kf/Uc583c440540142d89b55cc6fbde774106/969734566/Uc583c440540142d89b55cc6fbde774106.jpg",
                    "Nuts", 5.6, 4);
            allItems.add(walnut);

            GroceryItem pistachio = new GroceryItem("Pistachio", "The pistachio (/pɪˈstɑːʃiˌoʊ, -ˈstæ-/, Pistacia vera), a member of the cashew family, is a small tree originating from Central Asia and the Middle East. The tree produces seeds that are widely consumed as food. Pistacia vera often is confused with other species in the genus Pistacia that are also known as pistachio.",
                    "https://sc01.alicdn.com/kf/UTB8kYzuIlahduJk43Jaq6zM8FXaz.jpg",
                    "Nuts", 9.85, 15);
            allItems.add(pistachio);

            for (GroceryItem g : allItems) {
                groceryItemDao.insert(g);
            }

            return null;
        }
    }
}
