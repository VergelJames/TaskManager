package jamesph.TaskManager.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UtilityService {
  private static final String LOG_FORMAT = "{} : {}";
  private static final String ERR_LOG_FORMAT = "{}::{} ->";

  public static void logError(String signature, String msg) {
    log.error(LOG_FORMAT, signature, msg);
  }

  public static void logError(String signature, String msg, Exception ex) {
    log.error(ERR_LOG_FORMAT, signature, msg, ex);
  }

  public static void logInfo(String signature, String msg) {
    log.info(LOG_FORMAT, signature, msg);
  }

  public static void logWarning(String signature, String msg) {
    log.warn(LOG_FORMAT, signature, msg);
  }

}
