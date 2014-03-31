package com.winchannel.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * @@author lidongbo
 */
public class PageComponent {
	
    private int page = 1; // 当前页

    public int totalPages = 0; // 总页数

    private int pageRecorders;// 每页数据

    private int totalRows = 0; // 总数据数

    private int pageStartRow = 0;// 每页的起始数

    private int pageEndRow = 0; // 每页显示数据的终止数

    private boolean hasNextPage = false; // 是否有下一页

    private boolean hasPreviousPage = false; // 是否有前一页

    private List<?> list;

    public PageComponent(List<?> list, int pageRecorders) {
        init(list, pageRecorders); 
    }
    
    /**  
     * 初始化list，并告之该list每页的记录数
     * @@param list
     * @@param pageRecorders
     */
    public void init(List<?> list, int pageRecorders) {
        this.pageRecorders = pageRecorders;
        this.list = list;
        totalRows = list.size();
        hasPreviousPage = false;
        if(pageRecorders==0){
            totalPages=1;
        }else{
            if ((totalRows % pageRecorders) == 0) {
                totalPages = totalRows / pageRecorders;
            } else {
                totalPages = totalRows / pageRecorders + 1;
            }
        }
        if (page >= totalPages) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }

        if (totalRows < pageRecorders) {
            this.pageStartRow = 0;
            this.pageEndRow = totalRows;
        } else {
            this.pageStartRow = 0;
            this.pageEndRow = pageRecorders;
        }
    }
    public boolean isNext() {
        return list.size() > pageRecorders;
    }
    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }
    public String toString(int temp) {
        String str = Integer.toString(temp);
        return str;
    }

    public List<?> getNextPage() {
        page = page + 1;
        disposePage();
        return getPage(page);
    }

    private void disposePage() {
        if (page == 0) {
            page = 1;
        }
        if ((page - 1) > 0) {
            hasPreviousPage = true;
        } else {
            hasPreviousPage = false;
        }
        if (page >= totalPages) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }
    }
    public List<?> getPreviousPage() {
        page = page - 1;
        if ((page - 1) > 0) {
            hasPreviousPage = true;
        } else {
            hasPreviousPage = false;
        }
        if (page >= totalPages) {
            hasNextPage = false;
        } else {
            hasNextPage = true;
        }
        return getPage(page);
    }

    /**  
     * 获取第几页的内容
     * @@param page
     * @@return
     */
    public List<?> getPage(int page) {
        if (page == 0)
            this.page=1;
        else
            this.page=page;
        disposePage();
        if (page * pageRecorders < totalRows) {// 判断是否为最后一页
            pageEndRow = page * pageRecorders;
            pageStartRow = pageEndRow - pageRecorders;
        } else {
            pageEndRow = totalRows;
            pageStartRow = pageRecorders * (totalPages - 1);
        }
        List<?> objects = null;
        if (!list.isEmpty()) {
            objects = list.subList(pageStartRow, pageEndRow);
        }
        return objects;
    }
    public List<?> getFistPage() {
        if (isNext()) {
            return list.subList(0, pageRecorders);
        } else {
            return list;
        }
    }
    public boolean isHasNextPage() {
        return hasNextPage;
    }
    public List<?> getList() {
        return list;
    }
    public int getPage() {
        return page;
    }
    public int getPageEndRow() {
        return pageEndRow;
    }
    public int getPageRecorders() {
        return pageRecorders;
    }
    public int getPageStartRow() {
        return pageStartRow;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public int getTotalRows() {
        return totalRows;
    }
    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }
    
    public static void main(String args[]) {
//        List<String> list = new ArrayList<String>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        list.add("d");
//        list.add("e");
//        list.add("f");
//        list.add("g");
//        list.add("h");
//        list.add("h");
//        list.add("i");
//        list.add("j");
//        list.add("k");
//        list.add("l");
//        list.add("m");
        List paramList=new ArrayList();
        for (int i = 0; i < 9; i++) {
            Map map=new HashMap();
            for (int j = 0; j < 9; j++) {
                map.put(i+"行"+j+"列", i+""+j);
            }
            paramList.add(map);
        }
        PageComponent pm = new PageComponent(paramList, 2);
        List executeList = pm.getPage(2);
        
        
        for(int i = 0; i < executeList.size(); i++) {
            Map map=(Map)executeList.get(i);
            System.out.print("第"+(i+1)+"行：\t");
            for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
                Object key = (Object) iterator.next();
                System.out.print(map.get(key)+"\t");
            }
            System.out.println();
            System.out.println("===========");
        }
        
        
        executeList = pm.getNextPage();
        for(int i = 0; i < executeList.size(); i++) {
            Map map=(Map)executeList.get(i);
            System.out.print("第"+(i+1)+"行：\t");
            for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
                Object key = (Object) iterator.next();
                System.out.print(map.get(key)+"\t");
            }
            System.out.println();
            System.out.println("===========");
        }
//        System.out.println(sublist.get(0));
    }

}
