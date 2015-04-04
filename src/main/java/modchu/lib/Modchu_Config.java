package modchu.lib;import java.io.BufferedReader;import java.io.BufferedWriter;import java.io.File;import java.io.FileReader;import java.io.FileWriter;import java.util.ArrayList;import java.util.ConcurrentModificationException;import java.util.HashMap;import java.util.LinkedList;import java.util.List;import java.util.Map;import java.util.Map.Entry;import java.util.concurrent.ConcurrentHashMap;public class Modchu_Config{	protected static ArrayList<String> failureShowModelList = new ArrayList<String>();	public static void writerConfig(File file, String[] s) {		Modchu_FileManager.writerFile(file, s);	}	public static void writerConfig(File file, List<String> list1) {		Modchu_FileManager.writerFile(file, list1);	}	public static void writerConfig(File file, Map map) {		Modchu_FileManager.writerFile(file, map);	}	public static void loadConfig(List configList, File file) {		// cfg設定項目読み込み showModel読み込み		List list = getCfgData().get(file);		int i1;		if (list == null) {			list = new ArrayList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;				while ((rl = breader.readLine()) != null) {					list.add(rl);					if (rl.startsWith("#")							| rl.startsWith("/")) {						if (!failureShowModelList.contains(rl)) failureShowModelList.add(rl);						continue;					}					if (rl.indexOf("showModel[]") != -1) {						configList.add(rl);						Modchu_Debug.mDebug("Modchu_Config loadConfig "+ file.toString() +" load " + rl);					}				}				getCfgData().put(file, list);				//Modchu_Debug.mDebug("Modchu_Config loadConfig o = "+o.toString());			} catch (Exception e) {				Modchu_Debug.systemLogDebug("Modchu_Config loadConfig "+ file.toString() +" load fail.", 2, e);				e.printStackTrace();			} finally {				try {					if (breader != null) breader.close();				} catch (Exception e) {				}			}		} else {			String s2;			for (int i = 0; i < list.size() ; i++) {				s2 = (String) list.get(i);				if (s2.startsWith("#")						| s2.startsWith("/")) {					if (!failureShowModelList.contains(s2)) failureShowModelList.add(s2);					continue;				}				if (s2.indexOf("showModel[]") != -1) {					configList.add(s2);					//Modchu_Debug.mDebug("Modchu_Config loadConfig 2 "+ file.toString() +" load " + s2);				}			}		}	}	public static Object loadConfig(File file, String s, Object o) {		// cfg設定項目読み込み		List<String> list = getCfgData().get(file);		if (list == null) {			list = new ArrayList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;								while ((rl = breader.readLine()) != null) {					int i1;					list.add(rl);					if (rl.startsWith("#")							| rl.startsWith("/")) continue;					if (rl.startsWith(s)) {						i1 = rl.indexOf('=');						if (i1 > -1) {							if (s.length() == i1) {								o = rl.substring(i1 + 1);								//Modchu_Debug.mDebug("cfg "+ file.toString() +" load ok s.length()="+s.length()+" i1="+i1+" rl="+rl);							} else {								//Modchu_Debug.mDebug("cfg "+ file.toString() +" load s.length()="+s.length()+" i1="+i1+" rl="+rl);							}						}					}				}				getCfgData().put(file, list);				//Modchu_Debug.mDebug("Modchu_Config loadConfig1 o = "+o.toString());			} catch (Exception e) {				Modchu_Debug.systemLogDebug("Modchu_Config loadConfig "+ file.toString() +" load fail.", 2, e);				e.printStackTrace();			 } finally {				 try {					 if (breader != null) breader.close();				 } catch (Exception e) {				 }			 }		} else {			String s2;			for (int i = 0; i < list.size() ; i++) {				s2 = (String) list.get(i);				if (s2.startsWith("#")						| s2.startsWith("/")) continue;				if (s2.startsWith(s)) {					int i1 = s2.indexOf('=');					//if (s.equalsIgnoreCase("shortcutKeysTextureName[6]")) Modchu_Debug.mDebug("Modchu_Config loadConfig2 s2 = "+s2+" s="+s+" s.length()="+s.length()+" i1="+i1);					if (i1 > -1							&& s.length() == i1) {						o = s2.substring(i1 + 1);						break;					}				}			}			//Modchu_Debug.mDebug("Modchu_Config loadConfig2 o = "+o.toString());		}		return o;	}	public static void writerSupplementConfig(File file, Map map) {//		//設定ファイルにない項目追加書き込み//		if (file.exists()//				&& file.canRead()//				&& file.canWrite()) {//			List<String> lines = new LinkedList();//			List<String> templist = new LinkedList();//			BufferedReader breader = null;//			Map tempMap = Modchu_Main.mapDeepCopy(map);//			//Modchu_Debug.mDebug("writerSupplementConfig tempMap.size()="+tempMap.size());//			try {//				breader = new BufferedReader(new FileReader(file));//				String rl;//				String s;//				String s1;//				while ((rl = breader.readLine()) != null) {//					lines.add(rl);//					String[] s0 = rl.split("=");//					if (s0 != null//							&& s0.length > 1) {//						templist.add(s0[0]);//					}//				}//				for (Entry<Object, Object> en : ((Map<Object, Object>) map).entrySet()) {//					Object key = en.getKey();//					s = key.toString();//					for (String s3 : templist) {//						//Modchu_Debug.mDebug("writerSupplementConfig s="+s+" s3="+s3);//						if (s.equals(s3)) {//							//Modchu_Debug.mDebug("writerSupplementConfig remove");//							tempMap.remove(s3);//							break;//						}//					}//				}//				for (Entry<Object, Object> en : ((Map<Object, Object>) tempMap).entrySet()) {//					Object key = en.getKey();//					Object value = en.getValue();//					s = key.toString();//					s1 = value.toString();//					lines.add(s+"="+s1);//					//Modchu_Debug.mDebug("writerSupplementConfig lines.add "+s+"="+s1);//				}//			} catch (Exception er) {//				Modchu_Debug.systemLogDebug("Modchu_Config saveParamater", 2, er);//				er.printStackTrace();//			} finally {//				try {//					if (breader != null) breader.close();//				} catch (Exception e) {//				}//			}//			writerConfig(file, lines);//		}	}	public static void writerSupplementConfig(File file, String[] k, String[] k1) {		//設定ファイルにない項目追加書き込み		if (file.exists()				&& file.canRead()				&& file.canWrite()) {			List lines = new LinkedList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;				String s;				String s1;				boolean[] e = new boolean[k.length];				boolean ee = false;				StringBuilder sb = new StringBuilder();				while ((rl = breader.readLine()) != null) {					for (int i = 0; i < k.length ; i++) {						s = k[i];						if(!e[i]) {							if (rl.startsWith(s)) {								int i1 = rl.indexOf('=');								if (i1 > -1) {									if (s.length() == i1) {										sb.delete(0, sb.length());										sb.append(s).append("=")										.append(k1[i]);										lines.add(sb.toString());										e[i] = true;										//Modchu_Debug.mDebug("saveParamater true rl=" + rl);										break;									}								}							}						}					}				}				// 読み込めない項目があったかチェック、読み込めない項目があると作成しなおし				Boolean e1 = false;				for (int i = 0; i < k.length; i++) {					if (e[i] == false) {						e1 = true;						continue;					}				}				if (e1) {					sb.delete(0, sb.length());					//Modchu_Debug.mDebug("cfg file save. e=" + l.toString());					for(int i = 0; i < k.length ; i++) {						if (!e[i]) {							s = k[i];							sb.append(s).append("=");							sb.append(k1[i]);							lines.add(sb.toString());							sb.delete(0, sb.length());							//Modchu_Debug.mDebug("saveParamater save. k["+i+"]=" + k[i]+" k1["+i+"]=" + k1[i]);						}					}				}			} catch (Exception er) {				Modchu_Debug.systemLogDebug("Modchu_Config saveParamater", 2, er);				er.printStackTrace();			} finally {				try {					if (breader != null) breader.close();				} catch (Exception e) {				}			}			writerConfig(file, lines);		}	}	public static void writerSupplementConfig(File file, List<String> list, List<String> list1) {		//設定ファイルにない項目追加書き込み		if (file.exists()				&& file.canRead()				&& file.canWrite()) {			List lines = new LinkedList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;				String s;				String s1;				boolean[] e = new boolean[list.size()];				boolean ee = false;				StringBuilder sb = new StringBuilder();				while ((rl = breader.readLine()) != null) {					for (int i = 0; i < list.size() ; i++) {						s = list.get(i);						if(!e[i]) {							if (rl.startsWith(s)) {								int i1 = rl.indexOf('=');								if (i1 > -1) {									if (s.length() == i1) {										sb.delete(0, sb.length());										sb.append(s).append("=")										.append(list1.get(i));										lines.add(sb.toString());										e[i] = true;										//Modchu_Debug.mDebug("saveParamater true rl=" + rl);										break;									}								}							}						}					}				}				// 読み込めない項目があったかチェック、読み込めない項目があると作成しなおし				Boolean e1 = false;				for (int i = 0; i < list.size(); i++) {					if (e[i] == false) {						e1 = true;						continue;					}				}				if (e1) {					sb.delete(0, sb.length());					//Modchu_Debug.mDebug("cfg file save. e=" + l.toString());					for(int i = 0; i < list.size() ; i++) {						if (!e[i]) {							s = list.get(i);							sb.append(s).append("=");							sb.append(list1.get(i));							lines.add(sb.toString());							sb.delete(0, sb.length());							//Modchu_Debug.mDebug("saveParamater save. k["+i+"]=" + k[i]+" k1["+i+"]=" + k1[i]);						}					}				}			} catch (Exception er) {				Modchu_Debug.systemLogDebug("Modchu_Config saveParamater", 2, er);				er.printStackTrace();			} finally {				try {					if (breader != null) breader.close();				} catch (Exception e) {				}			}			writerConfig(file, lines);		}	}	public static ConcurrentHashMap<String, String> loadAllConfig(File file) {		ConcurrentHashMap<String, String> map = new ConcurrentHashMap();		// cfg設定項目すべて読み込みMapで返す		List<String> list = getCfgData().get(file);		int i1;		if (list == null) {			list = new ArrayList();			BufferedReader breader = null;			try {				breader = new BufferedReader(new FileReader(file));				String rl;				while ((rl = breader.readLine()) != null) {					list.add(rl);				}				getCfgData().put(file, list);				//Modchu_Debug.mDebug("Modchu_Config loadConfig o = "+o.toString());			} catch (Exception e) {				Modchu_Debug.systemLogDebug("Modchu_Config loadConfig "+ file.toString() +" load fail.", 2, e);				e.printStackTrace();			} finally {				try {					if (breader != null) breader.close();				} catch (Exception e) {				}			}		}		if (list != null				&& !list.isEmpty()); else return null;		for (String rl : list) {			if (rl.startsWith("#")					| rl.startsWith("/")) {				continue;			}			i1 = rl.indexOf("=");			if (i1 > -1) {				String s = rl.substring(0, i1);				String s1 = rl.substring(i1 + 1);				map.put(s, s1);			}		}		return map;	}	public static HashMap<Object, List<String>> getCfgData() {		return Modchu_FileManager.listData;	}}