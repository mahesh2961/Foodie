package in.foodie.adapters;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.core.content.res.TypedArrayUtils;
import androidx.room.TypeConverter;

/**
 * Converter is used Rooms Database to store Ingredients List
 */
public class Converter implements Serializable {

    /**
     * Converts comma seperated String to List
     * @param value
     * @return List<String>
     */
    @TypeConverter
    public List<String> fromStrToList(String value) {
        List<String> result=new ArrayList<>();

        if(!TextUtils.isEmpty(value)) {
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            result = new Gson().fromJson(value, listType);
        }
        return result;
    }

    /**
     *
     * @param strList
     * @return String
     */
    @TypeConverter
    public String fromListToStr(List<String> strList) {
        String value = "";
        if(strList!=null && strList.size()>0) {
            Gson gson = new Gson();
            value = gson.toJson(strList);
        }
        return value;
    }
}

