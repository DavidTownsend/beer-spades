package com.lol.beer_spades.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by davidtownsend on 11/5/15.
 */
// TODO Use constants instead they require less resources
public enum SuitType {
    @SerializedName("0") spades, @SerializedName("1") hearts, @SerializedName("2")  clubs, @SerializedName("3") diamonds
}