package com.example.myapplication;

import org.junit.Assert;
import org.junit.Test;

public class pomodroTest {
    @Test
    public void pomodro() {
        test Test = new test();
        int toplamZaman = 25;
        int harcananZaman = 12;
        int kalan = Test.pomodroSuresi(toplamZaman,harcananZaman);
        Assert.assertEquals(13, kalan);

    }
}
