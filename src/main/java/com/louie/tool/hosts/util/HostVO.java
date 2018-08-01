package com.louie.tool.hosts.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Host对象
 *
 * @author Louie
 */
public class HostVO {
    private String ip;
    private String domain;
    private String comment;
    private String remark;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }

        if (o == null || getClass() != o.getClass()) { return false; }

        HostVO hostVO = (HostVO)o;

        return new EqualsBuilder()
            .append(ip, hostVO.ip)
            .append(domain, hostVO.domain)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(ip)
            .append(domain)
            .toHashCode();
    }

    @Override
    public String toString() {
        return (StringUtils.isBlank(comment) ? "" : (comment + "\n")) + ip + " " + domain;
    }
}
