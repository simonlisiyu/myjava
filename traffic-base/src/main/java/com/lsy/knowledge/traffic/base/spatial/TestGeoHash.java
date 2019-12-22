package com.lsy.knowledge.traffic.base.spatial;

import com.lsy.knowledge.traffic.base.domain.BoundingBox;
import com.lsy.knowledge.traffic.base.spatial.geohash.GeoHash;
import com.lsy.knowledge.traffic.base.spatial.geohash.GeoHashBoundingBoxQuery;
import com.lsy.knowledge.traffic.base.spatial.geohash.GeoHashQuery;

/**
 * @author lsy
 * 2017年12月26日
 */
public class TestGeoHash {

    /**
     * @param args
     */
    public static void main(String[] args) {
        //根据gps，获得geohash的id
        GeoHash gh = GeoHash.withCharacterPrecision(40.024561, 116.303381, 6);
        System.out.println(gh.toBase32());

        //根据矩形框，获取所有包含的geohash的id
        GeoHashQuery search = new GeoHashBoundingBoxQuery(new BoundingBox(40.019333, 40.059943,  116.255495, 116.326391), 6);
        for (GeoHash g : search.getSearchHashes()) {
            System.out.println(g.toBase32());
        }
    }

}
