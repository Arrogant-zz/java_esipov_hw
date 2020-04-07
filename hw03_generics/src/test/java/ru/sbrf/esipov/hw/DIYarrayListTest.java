package ru.sbrf.esipov.hw;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DIYarrayListTest {

    @Test
    void testSetGet() {
        DIYarrayList<Integer> myArrayList = new DIYarrayList<>(3);

        myArrayList.set(2, 3);
        assertEquals((Integer) 3, myArrayList.get(2));
        myArrayList.set(2, 2);
        assertEquals((Integer) 2, myArrayList.get(2));
    }

    @Test
    void testAddAll() {
        DIYarrayList<Integer> myArrayList = new DIYarrayList<>();
        Collections.addAll(myArrayList, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);

        for(int i = 0; i < 14; i++) {
            assertEquals(myArrayList.get(i), (Integer) (i + 1));
        }
    }

    @Test
    void testBigSize() {
        DIYarrayList<Integer> myArrayList = new DIYarrayList<>();

        for(int i = 0; i < 10000; i++) {
            myArrayList.add(i);
        }

        assertEquals(10000, myArrayList.size());
    }

    @Test
    void testSort() {
        DIYarrayList<Integer> myArrayList = new DIYarrayList<>();
        Collections.addAll(myArrayList, 5, 4, 3, 1, 2, 6, 7, 8, 10, 9);
        Collections.sort(myArrayList);

        for(int i = 0; i < 10; i++) {
            assertEquals(myArrayList.get(i), (Integer) (i + 1));
        }
    }

    @Test
    void testCopy() {
        DIYarrayList<Integer> FirstList = new DIYarrayList<>();
        DIYarrayList<Integer> SecondList = new DIYarrayList<>(14);
        Collections.addAll(FirstList, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        Collections.copy(SecondList, FirstList);

        for(int i = 0; i < 14; i++) {
            assertEquals(SecondList.get(i), (Integer) (i + 1));
        }
    }

}