package com.ziwenl.horizontalgridview.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author : Ziwen Lan
 * Date : 2017/6/8
 * Time : 11:17
 * 网络图片链接库
 */

public class NetImageUtil {
    /**
     * 轮播图链接
     */
    public static String[] banners = new String[]{
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/3c0ad4926f8fb6b3e9d899137f0ddab0.jpg?w=2452&h=920",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/6bd4174b8c5aad67a64864a5716ad152.jpg?thumb=1&w=1226&h=460&f=webp&q=90",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/755aca9487082e7698e16f17cfb70839.jpg?thumb=1&w=1226&h=460&f=webp&q=90",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/3ede5bd72836ae1630bbd1b6cc9dabe0.jpg?thumb=1&w=1226&h=460&f=webp&q=90",
            "https://cdn.cnbj1.fds.api.mi-img.com/mi-mall/dcb09c80c58dc9d71623c925739a3733.jpg?thumb=1&w=1226&h=460&f=webp&q=90"
    };

    /**
     * 横向滑动的图片链接
     */
    private static String[] gridPicArray = new String[]{
            "https://img20.360buyimg.com/babel/s590x470_jfs/t1/104722/38/14232/92238/5e61a566Edd03bb7a/2277cc18adbf271b.jpg.webp",
            "https://img10.360buyimg.com/da/s590x470_jfs/t1/90017/32/13913/96950/5e606fc6Eabb9a5cb/450d83cc7ec141ea.jpg.webp",
            "https://img11.360buyimg.com/pop/s590x470_jfs/t1/103557/6/14052/88126/5e5efa66E8b8e2594/71b285b0414267a8.jpg.webp",
            "https://imgcps.jd.com/ling/27994751891/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5bd8253082acdd181d02fa7f/3278e592/590x470.jpg",
            "https://img20.360buyimg.com/pop/s590x470_jfs/t1/108383/31/6462/82701/5e4deda2Ed402d1a8/3b095fecaa636b9d.jpg.webp",
            "https://img10.360buyimg.com/pop/s590x470_jfs/t1/88472/10/14063/79504/5e61b982E5aff6a3a/8c74053ca01cd150.jpg.webp",
            "https://img20.360buyimg.com/pop/s590x470_jfs/t1/95847/26/14341/92921/5e61f8c1E8859ee0f/3e64f0b1801aae30.jpg.webp",
            "https://img20.360buyimg.com/babel/s590x470_jfs/t1/104722/38/14232/92238/5e61a566Edd03bb7a/2277cc18adbf271b.jpg.webp",
            "https://img10.360buyimg.com/da/s590x470_jfs/t1/90017/32/13913/96950/5e606fc6Eabb9a5cb/450d83cc7ec141ea.jpg.webp"
    };

    /**
     * @param posistion 获取第几张图片地址
     * @return
     */
    public static String getPicUrl(int posistion) {
        return gridPicArray[posistion % gridPicArray.length];
    }


    public static List<String> getUrls(int count) {
        List<String> gridPicList = new ArrayList<>();
        for (int position = 0; position < count; position++) {
            gridPicList.add(gridPicArray[position % gridPicArray.length]);
        }
        return gridPicList;
    }

    /**
     * @return 横向滑动图片集
     */
    public static List<String> getGridPicList() {
        return new ArrayList<>(Arrays.asList(gridPicArray));
    }

    public static List<String> getBannerList() {
        return new ArrayList<>(Arrays.asList(banners));
    }

}
