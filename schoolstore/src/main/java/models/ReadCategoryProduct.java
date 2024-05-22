package models;

import java.util.TreeMap;

public class ReadCategoryProduct {

    //準備好產品清單  
    public static TreeMap<String, Product> readProduct() {
        //read_product_from_file(); //從檔案或資料庫讀入產品菜單資訊
        //放所有產品  產品編號  產品物件
        TreeMap<String, Product> product_dict = new TreeMap<>();
        String[][] product_array = {
            {"p-s-115", "零食", "巧克力餅乾", "30", "chocolatecooke.jpg", "產品描述"},
            {"p-s-116", "零食", "洋芋片", "25", "potatochips.jpg", "產品描述"},
            {"p-s-117", "零食", "爆米花", "20", "p.jpg", "產品描述"},
            {"p-c-115", "零食", "蘇打餅乾", "30", "sodac.jpg", "產品描述"},
            {"p-c-116", "零食", "王子麵", "25", "queenc.jpg", "產品描述"},
            {"p-c-117", "零食", "牛奶餅乾", "20", "milkec.jpg", "產品描述"},
            {"p-b-118", "飲料", "可樂", "35", "cola.jpg", "產品描述"},
            {"p-b-119", "飲料", "運動飲料", "30", "sportdrink.jpg", "產品描述"},
            {"p-b-120", "飲料", "果汁混合飲料", "40", "mixedfruitjuice.jpg", "產品描述"},
            {"p-st-121", "文具", "筆記本", "35", "notebook.jpg", "產品描述"},
            {"p-st-122", "文具", "原子筆", "25", "pen.jpg", "產品描述"},
            {"p-st-123", "文具", "橡皮擦", "30", "eraser.jpg", "產品描述"},
            {"p-ot-124", "其他", "衛生紙", "10", "t.jpg", "產品描述"},
            {"p-ot-125", "水果", "香蕉", "30", "Ba.jpg", "產品描述"},
            {"p-ot-126", "水果", "蘋果", "25", "Apple.jpg", "產品描述"},
            {"p-ot-127", "水果", "哈密瓜", "50", "hami melon.jpg", "產品描述"},
            {"p-ot-128", "書本", "三隻小豬", "100", "big book.jpg", "產品描述"},
            {"p-ot-129", "書本", "小紅帽", "100", "little red.jpg", "產品描述"},
            {"p-ot-130", "其他", "水壺", "150", "waterbottle.jpg", "產品描述"}
        };

        //一筆放入字典變數product_dict中
        for (String[] item : product_array) {
            Product product = new Product(
                    item[0],
                    item[1],
                    item[2],
                    Integer.parseInt(item[3]), //價格轉為int
                    item[4],
                    item[5],
                    0.0); // Default discount set to 0.0%
            //將這一筆放入字典變數product_dict中 
            product_dict.put(product.getProduct_id(), product);
        }
        return product_dict;
    }
}
