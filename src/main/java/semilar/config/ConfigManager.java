package semilar.config;

public class ConfigManager
{
  private static String semilarUserDataRootFolder = "";
  private static String semilarHomeFolder = "";
  
  public static String getSemilarDataRootFolder()
  {
    return semilarUserDataRootFolder;
  }
  
  public static void setSemilarDataRootFolder(String semilarDataRootFolder)
  {
    semilarUserDataRootFolder = semilarDataRootFolder;
  }
  
  public static String getSemilarHomeFolder()
  {
    return semilarHomeFolder;
  }
  
  public static void setSemilarHomeFolder(String semilarHome)
  {
    semilarHomeFolder = semilarHome;
  }
  
  public static String getTasaLsaSpaceDir()
  {
    return semilarUserDataRootFolder + "LSA-TasaCorpus/";
  }
  
  public static String getWordnetDictionaryPath()
  {
    return semilarHomeFolder +"WordNet-JWI/3.0/dict";
  }
  
  public static String getWordnetPath()
  {
    return semilarHomeFolder +"WordNet-JWI/";
  }
  
  public static String getWordnetVersion()
  {
    return "3.0";
  }
  
  public static String getOpenNLPVersion()
  {
    return "1.5.0";
  }
  
  public static String getOpenNlpPath()
  {
    return semilarHomeFolder +"opennlp-tools-1.5.0/Models-1.5/";
  }
  
  public static String getWornetCacheFilePath()
  {
    return getWordnetPath() + "cache.dat";
  }
  
  public static String getLDAModelsRootFolderPath()
  {
    return semilarUserDataRootFolder + "LDA-MODELS/";
  }
  
  public static String getPMICorpusRootFolderPath()
  {
    return semilarUserDataRootFolder + "PMI-DATA/";
  }
  
  public static int getPMICacheSize()
  {
    return 10000;
  }
  
  public static String LSAModelRootFolderPath()
  {
    return semilarUserDataRootFolder + "LSA-MODELS/";
  }
}
