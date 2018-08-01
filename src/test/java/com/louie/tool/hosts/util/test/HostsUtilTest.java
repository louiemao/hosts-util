package com.louie.tool.hosts.util.test;

import com.louie.tool.hosts.util.HostVO;
import com.louie.tool.hosts.util.HostsUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HostsUtilTest {

    @Test
    public void HostsToExcel() {
        try {
            List<HostVO> list1 = HostsUtil.readHosts("/Users/xxx/IdeaProjects/hosts-util/hosts1.txt", "1");
            List<HostVO> list2 = HostsUtil.readHosts("/Users/xxx/IdeaProjects/hosts-util/hosts2.txt", "2");
            List<HostVO> list3 = HostsUtil.readHosts("/Users/xxx/IdeaProjects/hosts-util/hosts3.txt", "3");

            Set<HostVO> set = new HashSet<HostVO>();
            set.addAll(list1);
            set.addAll(list2);
            set.addAll(list3);
            List<HostVO> list = new ArrayList<HostVO>(set);

            HostsUtil.writeExcel(list, "/Users/xxx/IdeaProjects/hosts-util/hosts.xlsx");

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ExcelToHosts() {
        try {
            List<HostVO> list = HostsUtil.readExcel("/Users/xxx/IdeaProjects/hosts-util/hosts.xlsx");
            HostsUtil.writeHosts(list, "/Users/xxx/IdeaProjects/hosts-util/hosts-final.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
