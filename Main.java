package com.company;

import javax.sound.sampled.*;
import javax.xml.namespace.QName;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static int size =10;
    public static char[][] earth = new char[10][10];
    public static int x;
    public static int y;
    public static int x_1;
    public static int y_1;
    public static int S;
    public static int time=0;

    //Game Variable
    public static int farmer = 5;
    public static int warior = 1;
    public static int solder =0;

    public static int food = 100;
    public static int wood = 100;
    public static int stone = 100;
    public static int gold = 100;

    public static int farm = 0;
    public static int sawmill= 0;
    public static int career = 0;
    public static int mine = 0;

    public static void createlogig() {

    }

    public static void show_maps(){
        for (int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                System.out.print(earth[i][j]);
            }
            System.out.println();
        }
    }

    public static void initializatoin(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                earth[i][j]='.';
            }
        }
    }

    public static void created_castle(int x,int y){
        earth[y][x]='@';
    }

    public static void show_info_castle(){
        System.out.println("<Ваш слуга>------------------------------------------");
        System.out.println("Ваш слуга:");
        System.out.print("Крестян :");System.out.println(farmer);
        System.out.print("Мечники :");System.out.println(warior);
        System.out.print("Еда :");System.out.println(food);
        System.out.print("Дерево :");System.out.println(wood);
        System.out.print("Камень :");System.out.println(stone);
        System.out.print("Золото :");System.out.println(gold);
        module_speak();
    }

    public static void module_speak() {
        System.out.println("Ваши приказания милорд: ");
        Scanner in = new Scanner(System.in);
        String answer = in.next();
        if (answer.equals("Посмотреть_все_просьбы")==true) {
            //Написать дерево развития, и несколько навыков новых убрать отображение карты
            System.out.println("Солдат:: Я один, может призовем еще одного?(-10 золота, -1 крестьянин, +1 воин)");
            answer = in.next();
            if (answer.equals("да")) {
                farmer--;
                warior++;
                gold-=10;
            }
            System.out.println("Построить один дом для крестьян?(-10 дерева, +2 крестьянина, -10 золота, -5 камня)");
            answer = in.next();
            if (answer.equals("да")) {
                farmer+=2;
                gold-=10;
                wood-=10;
                stone-=5;
            }
        }
        if (answer.equals("demo")){
            food+=1000;
            wood+=1000;
            stone+=1000;
            farmer+=500;
            warior+=100;
        }
        if (answer.equals("show(time)")){
            System.out.println("Осталось:"+time);
        }
        System.out.println("Нужно назначить крестьян.");
        get_incame();
    }

    public static void get_incame(){
        int workers = 0;
        Scanner in =new Scanner(System.in);

        while(workers < farmer){
            System.out.println("("+(farmer-workers)+")Сколько крестьян отправить на поля?");
            int answer = in.nextInt();
            farm+=answer;
            workers+=answer;
            if (workers-farmer==0)break;


            System.out.println("("+(farmer-workers)+")Сколько крестьян отправить на лесопилки?");
            answer = in.nextInt();
            sawmill+=answer;
            workers+=answer;
            if (workers-farmer==0)break;

            System.out.println("("+(farmer-workers)+")Сколько крестьян отправить в карьеры?");
            answer = in.nextInt();
            career+=answer;
            workers+=answer;
            if (workers-farmer==0)break;

            System.out.println("("+(farmer-workers)+")Сколько крестьян отправить в шахты?");
            answer+= in.nextInt();
            mine=answer;
            workers+=answer;

        }
    }

    public static void start_income(){
        food+=farm*2;
        food-=farmer;
        food-=warior*3;
        farm = 0;

        wood+=sawmill;
        stone+=0.5*career;
        gold+=0.5*mine;
        gold-=0.05*warior;

        sawmill=0;
        career=0;
        mine=0;
    }

    public static void fun(){
        SourceDataLine clipSDL;
        clipSDL = null;
        AudioInputStream ais=null;
        byte[] b = new byte[2048];
        try {
            File f = new File("Yes.wav");
            ais = AudioSystem.getAudioInputStream(f);
            AudioFormat af = ais.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
            if (AudioSystem.isLineSupported(info)) {
                clipSDL = (SourceDataLine)AudioSystem.getLine(info);
                clipSDL.open(af);
                clipSDL.start();
                int num=0;
                while ((num=ais.read(b))!=-1)
                    clipSDL.write(b, 0, num);
                clipSDL.drain();
                ais.close();
                clipSDL.stop();
                clipSDL.close();
            }else
            {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public static void check_wariro(){
        Scanner in = new Scanner(System.in);

        if (time!=0){
            time--;
        }

        if (warior>=10&& time ==0){
            System.out.println("<-------------------<Теперь вы можете отпралять людей в походы>--------------------->");
            System.out.println("На какие территории нам отправить нашу армию?");
            x_1 =in.nextInt();
            y_1 =in.nextInt();
            if(y>=0&&y<size&&x>=0&&x<size){
                System.out.println("Считаем сколько занимает путь...");
                S = (int)(Math.sqrt((x-x_1)*(x-x_1)+(y-y_1)*(y-y_1)));
                time = S/2;
                solder+=warior;
                warior-=warior;
                System.out.println(time);
                System.out.println("Войско отправленно...(Пока не отображаеться(Когда придет +100 золото, +20 крестьян(пока не реализовано генерация карты)))");
            }
            //show_maps();
        }

        if (time == 0&& solder!=0){
            System.out.println("<-------------------->Наше войско вернулось с победой и с добычей, ура нашей армии!!!<----------------->");
            fun();
            warior+=solder;
            solder=0;
            gold+=100;
            farmer+=20;
        }

    }

    public static void main(String[] args) {
        initializatoin();
        fun();
        Scanner in =new Scanner(System.in);
	    created_castle(2,2);
	    //show_maps();
	    while (true){
            show_info_castle();
            start_income();
            check_wariro();
        }
    }
}
