package com.example.wwmeet_android.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindAnalysisResponse {

    private String data;
    private String data1;

    public String getData() {
        return data;
    }


    public String getData1() {
        return data1;
    }

    //    @SerializedName("food_comment")
//    private String analysisProbability;
//    @SerializedName("input_text")
//    private String analysisAdvice;
//    @SerializedName("predicted_emotion")
//    private String analysisTypeFood;
//    @SerializedName("probability")
//    private double analysisEmotionFood;
//
//    @SerializedName("recommended_food")
//    private String analysisEmotionFood2;
//
//
//    public FindAnalysisResponse(String analysisEmotion, String analysisProbability, String analysisAdvice, String analysisTypeFood, double analysisEmotionFood, String analysisEmotionFood2) {
//        this.analysisEmotion = analysisEmotion;
//        this.analysisProbability = analysisProbability;
//        this.analysisAdvice = analysisAdvice;
//        this.analysisTypeFood = analysisTypeFood;
//        this.analysisEmotionFood = analysisEmotionFood;
//        this.analysisEmotionFood2 = analysisEmotionFood2;
//    }
//
//    public FindAnalysisResponse(String analysisEmotion, String analysisProbability, String analysisAdvice,
//                                String analysisTypeFood, double analysisEmotionFood) {
//        this.analysisEmotion = analysisEmotion;
//        this.analysisProbability = analysisProbability;
//        this.analysisAdvice = analysisAdvice;
//        this.analysisTypeFood = analysisTypeFood;
//        this.analysisEmotionFood = analysisEmotionFood;
//    }
//
//    public String getAnalysisEmotion() {
//        return analysisEmotion;
//    }
//
//    public void setAnalysisEmotion(String analysisEmotion) {
//        this.analysisEmotion = analysisEmotion;
//    }
//
//    public String getAnalysisProbability() {
//        return analysisProbability;
//    }
//
//    public void setAnalysisProbability(String analysisProbability) {
//        this.analysisProbability = analysisProbability;
//    }
//
//    public String getAnalysisAdvice() {
//        return analysisAdvice;
//    }
//
//    public void setAnalysisAdvice(String analysisAdvice) {
//        this.analysisAdvice = analysisAdvice;
//    }
//
//    public String getAnalysisTypeFood() {
//        return analysisTypeFood;
//    }
//
//    public void setAnalysisTypeFood(String analysisTypeFood) {
//        this.analysisTypeFood = analysisTypeFood;
//    }
//
//    public double getAnalysisEmotionFood() {
//        return analysisEmotionFood;
//    }
//
//    public void setAnalysisEmotionFood(double analysisEmotionFood) {
//        this.analysisEmotionFood = analysisEmotionFood;
//    }
}
