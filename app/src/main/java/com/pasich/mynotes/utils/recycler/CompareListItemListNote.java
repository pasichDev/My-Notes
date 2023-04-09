package com.pasich.mynotes.utils.recycler;

import com.pasich.mynotes.data.model.ItemListNote;

import java.util.List;
import java.util.Objects;

public class CompareListItemListNote {

    /**
     * Метод который сравнивает два списка на наличие изменений и возврщает false если списки не одинаковые
     *
     * @param list1 - список до изменений
     * @param list2 - список с возможними изменениями
     */
    public static boolean compareLists(List<ItemListNote> list1, List<ItemListNote> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            ItemListNote item1 = list1.get(i);
            ItemListNote item2 = list2.get(i);

            if (!Objects.equals(item1.getValue(), item2.getValue()) || item1.getDragPosition() != item2.getDragPosition() || item1.isChecked() != item2.isChecked()) {
                return false;
            }
        }

        return true;
    }

}
