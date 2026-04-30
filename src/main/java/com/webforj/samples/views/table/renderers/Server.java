package com.webforj.samples.views.table.renderers;

public class Server {
  private String hostname;
  private String os;
  private String region;
  private String status;
  private boolean online;
  private int cpuUsage;
  private int memoryUsage;
  private int diskUsage;
  private int uptime;

  /**
   * @return the hostname
   */
  public String getHostname() {
    return hostname;
  }

  /**
   * @param hostname the hostname to set
   */
  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  /**
   * @return the os
   */
  public String getOs() {
    return os;
  }

  /**
   * @param os the os to set
   */
  public void setOs(String os) {
    this.os = os;
  }

  /**
   * @return the region
   */
  public String getRegion() {
    return region;
  }

  /**
   * @param region the region to set
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the online
   */
  public boolean isOnline() {
    return online;
  }

  /**
   * @param online the online to set
   */
  public void setOnline(boolean online) {
    this.online = online;
  }

  /**
   * @return the cpuUsage
   */
  public int getCpuUsage() {
    return cpuUsage;
  }

  /**
   * @param cpuUsage the cpuUsage to set
   */
  public void setCpuUsage(int cpuUsage) {
    this.cpuUsage = cpuUsage;
  }

  /**
   * @return the memoryUsage
   */
  public int getMemoryUsage() {
    return memoryUsage;
  }

  /**
   * @param memoryUsage the memoryUsage to set
   */
  public void setMemoryUsage(int memoryUsage) {
    this.memoryUsage = memoryUsage;
  }

  /**
   * @return the diskUsage
   */
  public int getDiskUsage() {
    return diskUsage;
  }

  /**
   * @param diskUsage the diskUsage to set
   */
  public void setDiskUsage(int diskUsage) {
    this.diskUsage = diskUsage;
  }

  /**
   * @return the uptime
   */
  public int getUptime() {
    return uptime;
  }

  /**
   * @param uptime the uptime to set
   */
  public void setUptime(int uptime) {
    this.uptime = uptime;
  }
}
