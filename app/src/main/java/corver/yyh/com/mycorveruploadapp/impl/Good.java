package corver.yyh.com.mycorveruploadapp.impl;

import java.io.Serializable;


/**
 * 类功能描述：</br>
 * 商品实体类
 * 博客地址：http://blog.csdn.net/androidstarjack
 * 公众号：终端研发部
 * @author yuyahao
 * @version 1.0 </p> 修改时间：2017/10/12 </br> 修改备注：</br>
 */
public class Good implements Serializable {
    private String gName;
    private String gDes;
    private int num;

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgDes() {
        return gDes;
    }

    public void setgDes(String gDes) {
        this.gDes = gDes;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
