package entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author tangyucong
 * @Title: PageResult
 * @ProjectName pinyougouparent
 * @Description: TODO
 * @date 2018/9/218:07
 */
public class PageResult implements Serializable {

    private long total;
    private List rows;

    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
