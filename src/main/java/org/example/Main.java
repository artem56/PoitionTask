package org.example;

import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner("7\n" +
                "3 1 1 2\n" +
                "2 1 3\n" +
                "3 4 3 4\n" +
                "1 7 \n" +
                "1 6 \n" +

                "3\n" +
                "8 4 5\n" +
                "19 2 5\n" +
                "010 10 6\n" +
                "0\n"
                );
        int N, Size, Q, X, Y, S;
        String CurrentStr = in.nextLine();
        N = Integer.parseInt(CurrentStr);
        int[][] RecipeTable =new int [N-2][2];
        int[] FeasibilityTable = new int [N-2];
        //Заполняем матрицы нулями
        for(int i=0; i<N-2;i++){FeasibilityTable[i]=0;}

        for(int i=0; i<N-2;i++){

            for(int j=0;j<2;j++){
                RecipeTable[i][j]=0;
            }
        }
        //Получаем двумерный массив рецептов


                for(int i=1; i<=N-2; i++) {
                    CurrentStr = in.nextLine();
                    int[] CurArr = Arrays.stream(CurrentStr.split(" "))
                            .map(String::trim).mapToInt(Integer::parseInt).toArray();
                    for(int j=1; j<=CurArr[0];j++){
                        // Проверяем рецепт на выполняемость
                        if (CurArr[j]==i+2) {
                            FeasibilityTable[i-1]=-1;
                        }
                        else{
                        if (CurArr[j]>i+2) {
                            FeasibilityTable[i-1]=CurArr[j];
                        }
                        else{
                        //Считаем необходимое колличество базовых компонентов A и B зелья i+2
                        if (CurArr[j]==1){
                            RecipeTable[i-1][0]+=1;
                        }
                        else if (CurArr[j]==2){
                            RecipeTable[i-1][1]+=1;
                        }
                        else {
                            if(FeasibilityTable[CurArr[j]-3]==-1){
                                FeasibilityTable[i-1]=-1;
                            }
                            //если зелья ссылаются друг на друга то их нельзя создать
                            else if(FeasibilityTable[CurArr[j]-3]!=0){
                                //Если рецепт зелья ещё не готов, то мы указываем, что ссылаемся на него
                                FeasibilityTable[i-1]=CurArr[j];
                                int target = FeasibilityTable[CurArr[j]-3];
                                while(target != 0) {
                                    if(target==i+2){
                                        do{
                                        int self = target;
                                        target = FeasibilityTable[self-3];
                                        FeasibilityTable[self-3]=-1;} while(target!=-1 && target != i+2);
                                        break;
                                    }

                                    target = FeasibilityTable[target-3];

                                }
                            }
                            else{
                            RecipeTable[i-1][0]+=RecipeTable[CurArr[j]-3][0];
                                RecipeTable[i-1][1]+=RecipeTable[CurArr[j]-3][1];
                            }
                        }}
                        }}
                }

                //Досчитываем рецепты зелий
        boolean CHECK = true;
        while(CHECK) {
            for (int i = 0; i < N - 2; i++) {
                if (FeasibilityTable[i] == 0 || FeasibilityTable[i] == -1) {
                    continue;
                }
                if (FeasibilityTable[FeasibilityTable[i] - 3] == 0) {
                    RecipeTable[i][0] += RecipeTable[FeasibilityTable[i] - 3][0];
                    RecipeTable[i][1] += RecipeTable[FeasibilityTable[i] - 3][1];
                    FeasibilityTable[i]=0;
                }
            }
            for (int i = 0; i< N - 2; i++){
                if (FeasibilityTable[i]!=0 && FeasibilityTable[i]!=-1){
                    break;
                }
                if (i == N-3){
                    CHECK=false;
                }
            }
        }
        //Ответы на вопросы
        Q = in.nextInt();
        for(int i=1;i<=Q;i++) {
            X = in.nextInt();
            Y = in.nextInt();
            S = in.nextInt();
            if(X>=RecipeTable[S-3][0] && Y>=RecipeTable[S-3][1] && FeasibilityTable[S-3]==0){
                System.out.print(1);
            }
            else{
                System.out.print(0);
            }
        }


    }
}