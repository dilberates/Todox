package com.example.myapplication;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class yasamDongusu {

    @BeforeClass
    public static void beforeCls(){
        System.out.println("Giris islemi yapilmadi");
    }
    @AfterClass
    public static void afterCls(){
        System.out.println("Giris islemi yapildi");
    }
    @Before
    public void setUp() throws Exception{
        System.out.println("Giris islemi oncesi");
    }
    @After
    public void tearDown() throws Exception{
        System.out.println("Giris islemi sonrasi");
    }
    @Test
    public void testHelloWorld() throws Exception{
        System.out.println("Giris islemi test 1");
    }
    @Test
    public void testHelloWorld2() throws Exception{
        System.out.println("Giris islemi test 2");
    }
}
