package modchu.model;import java.io.File;import java.lang.reflect.Constructor;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import java.util.Map.Entry;import littleMaidMobX.LittleMaidMobX;import littleMaidMobX.models.AbstractModelBase;import littleMaidMobX.models.ModelMultiBase;import littleMaidMobX.textures.TextureBox;import littleMaidMobX.textures.TextureManager;import modchu.lib.Modchu_Debug;import modchu.lib.Modchu_Main;import modchu.lib.Modchu_Reflect;import modchu.lib.Modchu_TextureManagerBase;import modchu.lib.characteristic.Modchu_AS;import modchu.lib.characteristic.Modchu_CastHelper;import modchu.lib.characteristic.Modchu_IEntityCapsBase;import modchu.lib.characteristic.Modchu_ModelBase;import modchu.lib.characteristic.Modchu_ModelRenderer;import modchu.lib.characteristic.Modchu_TextureBoxBase;import modchu.lib.characteristic.Modchu_Version;import modchu.lib.lmm.characteristic.ModchuLmmXModel;import modchu.lib.lmm.characteristic.Modchu_LmmXTextureBox;import modchu.model.multimodel.base.MultiModel;import modchu.model.multimodel.base.MultiModelBaseBiped;import modchu.model.multimodel.base.MultiModelOtherModel;public class ModchuModel_Main {	public static final String version = "22f";	public static final String modName = "ModchuModel";	public static final String versionString = ""+ Modchu_Version.version + "-" + version;	public static String newVersion = "";	public final static boolean isLMM = true;	public final static boolean isLMMX = true;//	public final static boolean isPFLMF = false;//	public final static boolean isFavBlock = false;//	public final static boolean isDecoBlock = false;//	public final static boolean isBTW = false;//	public final static boolean isGulliver = false;//	public final static boolean isSSP = false;	public static boolean initModsLoaded = false;	public static HashMap<String, Object[]> checkModelsBox = new HashMap();	public static HashMap<Object, Map> entityModelMapData = new HashMap();	public static HashMap<String, Object[]> dummyModelMapData = new HashMap();	public static ArrayList<String> ngPlayerModelList = new ArrayList();	public static ArrayList<String> showModelList = new ArrayList();	public static ArrayList<String> modelNewInstanceNgList = new ArrayList();	public static String modelClassName = "MultiModel";	private static File mainCfgfile;	public static File cfgfile;	private static int initModsLoadedCount = 0;	public static final String addLmmModelString = ";lmmModel";	private static final int maxInitModsLoadedCount = 10;	public String getName() {		return modName;	}	public String getVersion() {		return versionString;	}	public static void load() {		Modchu_Debug.systemLogDebug("[ModchuModel_Main] 1 - (1 / 2) load()");//		mainCfgfile = new File(Modchu_Main.cfgdir, "ModchuModel.cfg");//		cfgfile = new File(Modchu_Main.cfgdir, "ModchuModel_ShowModel.cfg");//		loadcfg();		modsLoadedCountSetting();		if (initModsLoaded) return;		initModsLoadedCount = 0;		//Modchu_Debug.lDebug("ModchuModel_Main modsLoaded() "+initModsLoadedCount);		Modchu_ModelRenderer.defaultMasterClass = ModchuModel_ModelRendererMaster.class;		// テクスチャパック、モデルの読み込み		// 必須モデルのロードテスト		Modchu_Debug.lDebug("ModchuModel_Main modsLoaded() MultiModel load.");		MultiModelBaseBiped[] models = null;		try {			models = new MultiModel[3];			Modchu_Debug.lDebug("ModchuModel_Main modsLoaded() MultiModel load models="+models);			modsLoadedCountSetting();			models[0] = new MultiModel(0.0F);			if (models[0] != null); else {				Modchu_Main.setRuntimeException("MultiModel.class not found !!");			}			Modchu_Debug.lDebug("ModchuModel_Main modsLoaded() MultiModel load models[0]="+models[0]);			float[] f = ((MultiModel) models[0]).getArmorModelsSize();			models[1] = new MultiModel(f[0]);			models[2] = new MultiModel(f[1]);		} catch(Exception e) {			Modchu_Debug.systemLogDebug("ModchuModel_Main modsLoaded() MultiModel load models Exception !!");			Modchu_Debug.systemLogDebug("", e);			e.printStackTrace();		}		Modchu_Debug.lDebug("ModchuModel_Main modsLoaded() MultiModel load end.");		Modchu_TextureManagerBase.instance.init();		Modchu_TextureManagerBase.instance.loadTextures();		initTextureManager();		lmmTextureManagerInit();		initModsLoaded = true;	}	private static void modsLoadedCountSetting() {		initModsLoadedCount++;		Modchu_Debug.systemLogDebug(new StringBuilder().append("[ModchuModel_Main] 2 - (").append(initModsLoadedCount).append(" / ").append(maxInitModsLoadedCount).append(") modsLoaded()").toString());	}	public static void worldEventLoad(Object event) {		//Modchu_Debug.lDebug("ModchuModel_Main worldEventLoad ");		if (!initModsLoaded) Modchu_Main.setRuntimeException("ModchuModel_Main initModsLoaded error !! initModsLoadedCount="+initModsLoadedCount);		//Modchu_Debug.lDebug("ModchuModel_Main worldEventLoad 1");		//Modchu_Debug.lDebug("ModchuModel_Main worldEventLoad 2");		Class ModchuLmmModel = modchu.lib.lmm.characteristic.ModchuLmmXModel.class;		if (ModchuLmmModel != null); else return;		if (Modchu_Main.getMinecraftVersion() > 169) {			Map textures = Modchu_CastHelper.Map(TextureManager.getTextureList());			if (textures != null					&& !textures.isEmpty()); else {				return;			}			for (Entry<String, Object> en : ((Map<String, Object>) textures).entrySet()) {				Object ltb = en.getValue();				Object model = getTextureBoxModels(ltb);				if (model != null						&& ModchuLmmModel.isInstance(model)) {					Modchu_Reflect.invokeMethod(ModchuLmmModel, "worldEventLoad", new Class[]{ Object.class }, model, new Object[]{ event });				}			}		} else {			List textures = Modchu_CastHelper.List(TextureManager.getTextureList());			if (textures != null					&& !textures.isEmpty()); else {				return;			}			for (Object ltb : textures) {				Object model = getTextureBoxModels(ltb);				if (model != null						&& ModchuLmmModel.isInstance(model)) {					Modchu_Reflect.invokeMethod(ModchuLmmModel, "worldEventLoad", new Class[]{ Object.class }, model, new Object[]{ event });				}			}		}	}	private static void initTextureManager() {		if (!LittleMaidMobX.isServer) {			//ModchuModel_StabilizerManagerReplacePoint.loadStabilizer();			// テクスチャインデックスの構築			Modchu_TextureManagerBase.instance.initTextureList(true);		} else {//			Modchu_TextureManagerBase.instance.loadTextureServer();		}	}	private static void lmmTextureManagerInit() {		TextureManager instance = TextureManager.instance;		Map<String, ModelMultiBase[]> models = instance.modelMap;		List<TextureBox> textures = TextureManager.getTextureList();		for (Entry<String, MultiModelBaseBiped[]> en : Modchu_TextureManagerBase.instance.modelMap.entrySet()) {			MultiModelBaseBiped[] o = en.getValue();			if (o[0].getClass() == MultiModel.class) continue;			ModelMultiBase[] mlm = new ModelMultiBase[3];			mlm[0] = new ModchuLmmXModel(o[0].getClass(), 0.0F);			if (mlm[0] != null); else {				continue;			}			float[] lsize = mlm[0].getArmorModelsSize();			mlm[1] = new ModchuLmmXModel(o[0].getClass(), lsize[0]);			mlm[2] = new ModchuLmmXModel(o[0].getClass(), lsize[1]);						String a = ((ModchuLmmXModel)mlm[0]).master.getClass().getName();			String name = Modchu_Main.lastIndexProcessing(a, "_");			models.put(name, mlm);		}		String defaultModelName = TextureManager.defaultModelName;		ModelMultiBase[] ldm = models.get(defaultModelName);		if (ldm == null && !models.isEmpty()) {			ldm = (ModelMultiBase[]) models.values().toArray()[0];		}		List texturesFileName = new ArrayList();		for (TextureBox ltb1 : textures) {			String fileName = ltb1.fileName;			if (fileName != null					&& !fileName.isEmpty()) texturesFileName.add(fileName);		}		for (Modchu_TextureBoxBase mtb : Modchu_TextureManagerBase.instance.textures) {			TextureBox ltb = new Modchu_LmmXTextureBox(mtb);			if (!texturesFileName.contains(mtb.fileName)) {				textures.add(ltb);			}		}		for (TextureBox ltb : textures) {			String modelName = ltb.modelName;			if (modelName.isEmpty()) {				ltb.setModels(defaultModelName, null, ldm);			} else {				ModelMultiBase[] model1 = models.get(modelName);				ltb.setModels(modelName, model1, ldm);			}				}		for (Entry<String, ModelMultiBase[]> le : models.entrySet()) {			String ls =  le.getValue()[0].getUsingTexture();			if (ls != null) {				if (instance.getTextureBox(ls + "_" + le.getKey()) == null) {					TextureBox lbox = null;					for (TextureBox ltb : textures) {						String packegeName = ltb.packegeName;						if (packegeName != null								&& packegeName.equals(ls)) {							lbox = ltb;							break;						}					}					if (lbox != null) {						lbox = lbox.duplicate();						lbox.setModels(le.getKey(), null, le.getValue());						textures.add(lbox);					}				}			}		}		for (int li = textures.size() - 1; li >= 0; li--) {			TextureBox o = textures.get(li);			if (o.models == null) {				textures.remove(li);			}		}		instance.initTextureList(true);	}//	//	public static void loadcfg() {//		// cfg読み込み//		if (Modchu_Main.cfgdir.exists()) {//			if (!mainCfgfile.exists()) {//				// cfgファイルが無い = 新規作成//				String s[] = {//						"AlphaBlend=true", "skirtFloats=true", "skirtFloatsVolume=1.0D", "breastFloats=true", "breastFloatsVolume=1.0D",//						"transparency=1.0F", "modchuRemodelingModel=true", "useInvisibilityBody=true", "useInvisibilityArmor=false", "useInvisibilityItem=false",//						"versionCheck=true",  "debugCustomModelMessage=false", "modelForLittleMaidMob=true"//				};//				Modchu_Config.writerConfig(mainCfgfile, s);//			} else {//				// cfgファイルがある//				ModchuModel_ConfigData.AlphaBlend = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "AlphaBlend", ModchuModel_ConfigData.AlphaBlend));//				ModchuModel_ConfigData.skirtFloats = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "skirtFloats", ModchuModel_ConfigData.skirtFloats));//				ModchuModel_ConfigData.skirtFloatsVolume = Modchu_CastHelper.Double(Modchu_Config.loadConfig(mainCfgfile, "skirtFloatsVolume", ModchuModel_ConfigData.skirtFloatsVolume));//				ModchuModel_ConfigData.breastFloats = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "breastFloats", ModchuModel_ConfigData.breastFloats));//				ModchuModel_ConfigData.breastFloatsVolume = Modchu_CastHelper.Double(Modchu_Config.loadConfig(mainCfgfile, "breastFloatsVolume", ModchuModel_ConfigData.breastFloatsVolume));//				ModchuModel_ConfigData.transparency = Modchu_CastHelper.Float(Modchu_Config.loadConfig(mainCfgfile, "transparency", ModchuModel_ConfigData.transparency));//				ModchuModel_ConfigData.modchuRemodelingModel = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "modchuRemodelingModel", ModchuModel_ConfigData.modchuRemodelingModel));//				ModchuModel_ConfigData.useInvisibilityBody = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "useInvisibilityBody", ModchuModel_ConfigData.useInvisibilityBody));//				ModchuModel_ConfigData.useInvisibilityArmor = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "useInvisibilityArmor", ModchuModel_ConfigData.useInvisibilityArmor));//				ModchuModel_ConfigData.useInvisibilityItem = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "useInvisibilityItem", ModchuModel_ConfigData.useInvisibilityItem));////				ModchuModel_ConfigData.versionCheck = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "versionCheck", ModchuModel_ConfigData.versionCheck));////				ModchuModel_ConfigData.modelForLittleMaidMob = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "modelForLittleMaidMob", ModchuModel_ConfigData.modelForLittleMaidMob));////				Modchu_Debug.debugCustomModelMessage = Modchu_CastHelper.Boolean(Modchu_Config.loadConfig(mainCfgfile, "debugCustomModelMessage", Modchu_Debug.debugCustomModelMessage));//				String k[] = {//						"AlphaBlend", "skirtFloats", "skirtFloatsVolume", "breastFloats", "breastFloatsVolume",//						"transparency", "modchuRemodelingModel", "useInvisibilityBody", "useInvisibilityArmor", "useInvisibilityItem",//						"versionCheck", "debugCustomModelMessage", "modelForLittleMaidMob"//				};//				String k1[] = {//						""+ModchuModel_ConfigData.AlphaBlend, ""+ModchuModel_ConfigData.skirtFloats, ""+ModchuModel_ConfigData.skirtFloatsVolume, ""+ModchuModel_ConfigData.breastFloats, ""+ModchuModel_ConfigData.breastFloatsVolume,//						""+ModchuModel_ConfigData.transparency, ""+ModchuModel_ConfigData.modchuRemodelingModel, ""+ModchuModel_ConfigData.useInvisibilityBody, ""+ModchuModel_ConfigData.useInvisibilityArmor, ""+ModchuModel_ConfigData.useInvisibilityItem,//						""+ModchuModel_ConfigData.versionCheck, ""+Modchu_Debug.debugCustomModelMessage, ""+ModchuModel_ConfigData.modelForLittleMaidMob//				};//				Modchu_Config.writerSupplementConfig(mainCfgfile, k, k1);//			}//			if (cfgfile.exists()) Modchu_Config.loadConfig(showModelList, cfgfile);//		}//		cfgMaxMinCheck();//	}////	public static void cfgMaxMinCheck() {//		if (ModchuModel_ConfigData.skirtFloatsVolume < 0.0D) ModchuModel_ConfigData.skirtFloatsVolume = 0.0D;//		if (ModchuModel_ConfigData.skirtFloatsVolume > 2.0D) ModchuModel_ConfigData.skirtFloatsVolume = 2.0D;//		if (ModchuModel_ConfigData.breastFloatsVolume < 0.0D) ModchuModel_ConfigData.breastFloatsVolume = 0.0D;//		if (ModchuModel_ConfigData.breastFloatsVolume > 2.0D) ModchuModel_ConfigData.breastFloatsVolume = 2.0D;//		if (ModchuModel_ConfigData.transparency < 0.0F) ModchuModel_ConfigData.transparency = 0.0F;//		if (ModchuModel_ConfigData.transparency > 1.0F) ModchuModel_ConfigData.transparency = 1.0F;//	}/*	public static boolean onTickInGame(float f, Object minecraft, byte by, Object... tickData) {		return false;	}*/	public static String getPackege(int i, int j) {		return getTextureBoxTextureName(getTextureManagerTextures(j));	}	public static String getModelSpecificationArmorPackege(String s) {		s = Modchu_Main.lastIndexProcessing(s, "_");		String s1 = null;		Object ltb;		for (int i = 0 ; i < getTextureManagerTexturesSize() ; ++i) {			ltb = getTextureManagerTextures(i);			if (getTextureBoxHasArmor(ltb)) {				String s2 = Modchu_Main.lastIndexProcessing(getTextureBoxTextureName(ltb), "_");				if (s2 != null						&& !s2.isEmpty()						&& s2.equalsIgnoreCase(s)) {					//Modchu_Debug.mDebug("ltb.packegeName="+getTextureBoxFileName(ltb));					return getTextureBoxFileName(ltb);				}			}		}		return null;	}	public static Object getTextureBox(String s) {		s = textureNameCheck(s);		return Modchu_TextureManagerBase.instance.getTextureBox(s);	}	public static Object[] getTextureModels(Object entity, String s, Object[] option) {		return getTextureModels(entity, s, false, false, option);	}	public static Object[] getTextureModels(Object entity, String s, boolean b, boolean b1, Object[] option) {		if (b1) {			Object ltb = getTextureBox(s);			return Modchu_CastHelper.ObjectArray(Modchu_Reflect.getFieldObject(ltb.getClass(), "models", ltb));		} else {			return modelNewInstance(entity, s, b, true, option);		}	}	public static Object getTextureBox(int i) {		return getTextureManagerTextures(i);	}	public static Object[] getTextureModels(Object entity, int i, Object[] option) {		return getTextureModels(entity, i, false, false, option);	}	public static Object[] getTextureModels(Object entity, int i, boolean b, boolean b1, Object[] option) {		Object ltb = getTextureManagerTextures(i);		return b1 ? Modchu_CastHelper.ObjectArray(Modchu_Reflect.getFieldObject(ltb.getClass(), "models", ltb)) : modelNewInstance(entity, getTextureBoxTextureName(ltb), b, true, option);	}	public static Object[] modelNewInstance(Object entity, String s, boolean b, boolean useCustom, Object[] option) {		return modelNewInstance(entity, s, b, useCustom, option, true);	}	public static Object[] modelNewInstance(Object entity, String s, boolean b, boolean useCustom, Object[] option, boolean debug) {		if (s != null				&& !s.isEmpty()); else return null;		Map<String, Object[]> map = null;		Object[] models = null;		s = textureNameCheck(s);		String s1 = Modchu_Main.lastIndexProcessing(s, "_");		if (entity != null) map = entityModelMapData.get(entity);		else map = dummyModelMapData;		if (!b) {			if (map != null) {				models = map.get(s);				if (models != null						&& models[0] != null						&& models[1] != null						&& models[2] != null) {					if (debug) Modchu_Debug.lDebug("modelNewInstance 1 return models. s="+s);					return models;				} else {					if (debug) Modchu_Debug.lDebug("modelNewInstance 1 models == null s="+s);				}			}		}		if (map != null); else {			map = new HashMap<String, Object[]>();			//Modchu_Debug.mDebug("modelNewInstance map = new HashMap");		}		if (debug) Modchu_Debug.lDebug1("modelNewInstance 2 s1="+s1+" modelNewInstanceNgList.contains(s1)="+modelNewInstanceNgList.contains(s1));		models = modelNewInstanceNgList.contains(s1)				| s.lastIndexOf(ModchuModel_Main.addLmmModelString) > -1 ? getTextureModels(entity, s, false, true, option) : modelNewInstance(s, useCustom, option);		if (models != null				&& models[0] != null				&& models[1] != null				&& models[2] != null) {			map.put(s, models);			if (entity != null) entityModelMapData.put(entity, map);			if (debug) Modchu_Debug.lDebug("modelNewInstance 3 return models="+models);			return models;		}		if (debug) Modchu_Debug.lDebug("modelNewInstance 4 return models="+models);		return models;	}	public static Object[] modelNewInstance(String s, boolean useCustom, Object[] option) {		if (s != null				&& !s.isEmpty()); else return null;		Object[] models = new Object[3];		String s1 = s != null ? Modchu_Main.lastIndexProcessing(s, "_") : s;		String defaultModelName = Modchu_TextureManagerBase.defaultModelName;		boolean defaultFlag = s1 != null				&& (defaultModelName != null				&& s1.indexOf(defaultModelName) > -1);		String s2 = defaultFlag ? "modchu.model.multimodel.base.MultiModel" : new StringBuilder().append(modelClassName).append("_").append(s1).toString();		String s3 = getModelClassName(s2);		//Modchu_Debug.lDebug("modelNewInstance s3="+s3);		Class c = Modchu_Reflect.loadClass(s3, -1);		//Modchu_Debug.lDebug("modelNewInstance c="+c);		if (c != null				&& s.lastIndexOf(ModchuModel_Main.addLmmModelString) < 0) {			//Modchu_Debug.lDebug("modelNewInstance s="+s+" c="+c);			Object o = modelNewInstance(c, new Class[]{ float.class, float.class, int.class, int.class, Object[].class }, new Object[]{ 0.0F, 0.0F, -1, -1, option });			if (o != null); else {				o = modelNewInstance(c, new Class[]{ float.class }, new Object[]{ 0.0F });			}			MultiModelBaseBiped multiModelBaseBiped = o != null					&& o instanceof MultiModelBaseBiped ? (MultiModelBaseBiped) o : null;			if (multiModelBaseBiped != null) {				models[0] = multiModelBaseBiped;/*//125delete				if (isSmartMoving						&& mod_pflm_playerformlittlemaid.playerFormLittleMaidVersion <= 124) {					boolean isBiped = modc_PFLM_PlayerFormLittleMaid.BipedClass != null ?							modc_PFLM_PlayerFormLittleMaid.BipedClass.isInstance(models[0]) : s.equalsIgnoreCase("Biped");					float[] f1 = new float[2];					f1[0] = isBiped ? 0.5F : 0.1F;					f1[1] = isBiped ? 1.0F : 0.5F;					//Modchu_Debug.mDebug("4modelNewInstance o != null isBiped="+isBiped);					models[1] = Modchu_Reflect.newInstance(c, new Class[]{ float.class, int.class, int.class }, new Object[]{ f1[0], MultiModelSmart.NoScaleStart, MultiModelSmart.Scale });					models[2] = Modchu_Reflect.newInstance(c, new Class[]{ float.class, int.class, int.class }, new Object[]{ f1[1], MultiModelSmart.NoScaleStart, MultiModelSmart.NoScaleEnd });					//Modchu_Debug.mDebug("5modelNewInstance o != null models[1] != null ? "+(models[1] != null));				} else {*///125delete					float[] f1 = getArmorModelsSize(models[0]);					Modchu_Debug.mDebug("modelNewInstance getArmorModelsSize f1[0]="+f1[0]+" f1[1]="+f1[1]);					o = modelNewInstance(c, new Class[]{ float.class, float.class, int.class, int.class, Object[].class }, new Object[]{ f1[0], 0.0F, -1, -1, option });					if (o != null); else {						o = modelNewInstance(c, new Class[]{ float.class }, new Object[]{ f1[0] });					}					models[1] = o != null							&& o instanceof MultiModelBaseBiped ? (MultiModelBaseBiped) o : null;					o = modelNewInstance(c, new Class[]{ float.class, float.class, int.class, int.class, Object[].class }, new Object[]{ f1[1], 0.0F, -1, -1, option });					if (o != null); else {						o = modelNewInstance(c, new Class[]{ float.class }, new Object[]{ f1[1] });					}					models[2] = o != null							&& o instanceof MultiModelBaseBiped ? (MultiModelBaseBiped) o : null;/*//125delete				}*///125delete			} else {				Modchu_Debug.lDebug("modelNewInstance multiModelBaseBiped == null !! s="+s);			}		} else {			Object ltb = getTextureBox(s);			if (ltb != null) models = getTextureBoxModels(ltb);			Modchu_Debug.lDebug("modelNewInstance c == null ltb="+ltb+" s="+s+" models="+models);			if (models != null) {				int i = 0;				for (Object model : models) {					Modchu_Debug.lDebug("modelNewInstance c == null models["+i+"]="+model);					i++;				}			}			//if (useCustom					//&& !defaultFlag) models = newModelCustom(models, s);		}		return models;	}	private static Object modelNewInstance(Class c, Class[] c1, Object[] o) {		if (c != null); else return null;		Object o1 = null;		try {			Constructor co = Modchu_Reflect.getConstructor(c, c1, -1);			if (co != null) o1 = Modchu_Reflect.newInstance(c, c1, o);		} catch(Exception e) {			Modchu_Debug.systemLogDebug("modelNewInstance newInstance Exception !!");			Modchu_Debug.systemLogDebug("", e);		}		return o1;	}	public static Object[] newOtherModel(String s, String s1, String s2, Class[] c, Object[] o, float[] f, boolean isChild) {		Object[] models = new Object[3];		float[] f1 = f != null ? f : new float[]{				0.0F, 0.5F, 1.0F		};		//Modchu_Debug.mDebug("newOtherModel s="+s);		models[0] = getNewInstanceOtherModel(s, c, o, f1[0]);		if (models[0] != null); else return null;		Modchu_AS.set(Modchu_AS.modelBaseIsChild, models[0], isChild);		models[1] = s1 != null				&& !s1.isEmpty() ? getNewInstanceOtherModel(s1, c, o, f1[1]) : null;		Modchu_AS.set(Modchu_AS.modelBaseIsChild, models[1], isChild);		models[2] = s2 != null				&& !s2.isEmpty() ? getNewInstanceOtherModel(s2, c, o, f1[2]) : null;		Modchu_AS.set(Modchu_AS.modelBaseIsChild, models[2], isChild);		return models;	}	public static MultiModelOtherModel[] newMultiModelOtherModel(Object[] models, HashMap<String, Object> map) {		MultiModelOtherModel[] newModels = new MultiModelOtherModel[3];		newModels[0] = new MultiModelOtherModel(0.0F, models[0], map);		float[] f1 = newModels[0].getArmorModelsSize();		newModels[1] = new MultiModelOtherModel(f1[0], models[1], map);		newModels[2] = new MultiModelOtherModel(f1[1], models[2], map);		return newModels;	}//	public static MultiModelLMMModel[] newMultiModelLMMModel(ModelMultiBase[] models) {//		MultiModelLMMModel[] newModels = new MultiModelLMMModel[3];//		newModels[0] = new MultiModelLMMModel(0.0F, models[0]);//		float[] f1 = newModels[0].getArmorModelsSize();//		newModels[1] = new MultiModelLMMModel(f1[0], models[1]);//		newModels[2] = new MultiModelLMMModel(f1[1], models[2]);//		return newModels;//	}	private static Object getNewInstanceOtherModel(String s, Class[] c, Object[] o, float f) {		if (c != null) {			if (Modchu_Reflect.getConstructor(s, c, -1) != null) {				return Modchu_Reflect.newInstance(s, c, o);			}		}		int mode = Modchu_Reflect.getConstructor(s, new Class[]{ float.class }, -1) != null ? 0 : Modchu_Reflect.getConstructor(s, new Class[]{ float.class, boolean.class }, -1) != null ? 1 : -1;		switch (mode) {		case -1:			return Modchu_Reflect.newInstance(s);		case 0:			return Modchu_Reflect.newInstance(s, new Class[]{ float.class }, new Object[]{ f });		case 1:			return Modchu_Reflect.newInstance(s, new Class[]{ float.class, boolean.class }, new Object[]{ f, false });		}		return null;	}	public static Object checkTexturePackege(String s, int i) {		Object ltb = getTextureBox(s);		if (ltb != null) {			Object s1 = textureManagerGetTexture(s, i);			if (s1 != null) {				//Modchu_Debug.mDebug("checkTexturePackege ok s1="+s1);				return ltb;			}		}		return null;	}	public static Object checkTextureArmorPackege(String s) {		Modchu_Debug.mDebug("checkTextureArmorPackege s="+s);		Object ltb = getTextureBox(s);		Modchu_Debug.mDebug("checkTextureArmorPackege ltb="+ltb);		if (ltb != null				&& getTextureBoxHasArmor(ltb)) {			Object s1 = textureManagerGetArmorTexture(s, 64, Modchu_Reflect.newInstance("ItemStack", new Class[]{ Modchu_Reflect.loadClass("Item") }, new Object[]{ Modchu_AS.get(Modchu_AS.getItem, "diamond_helmet") }));			Modchu_Debug.mDebug("checkTextureArmorPackege s1="+s1);			if (s1 != null) {				Modchu_Debug.mDebug("checkTextureArmorPackege ok return.");				return ltb;			}		}		Modchu_Debug.mDebug("checkTextureArmorPackege return null.");		return null;	}	public static Object textureManagerGetTexture(String s, int i) {		Object ltb = getTextureBox(s);		//Modchu_Debug.mDebug("textureManagerGetTextureName s="+textureNameCheck(s)+" i="+i);		if (ltb != null) return getTextureBoxTextureName(ltb, i);		Modchu_Debug.Debug1("textureManagerGetTextureName null !! default change. s="+textureNameCheck(s)+" i="+i);		s = textureNameCheck(null);		ltb = getTextureBox(s);		if (ltb != null) return getTextureBoxTextureName(ltb, i);		return null;	}	public static boolean textureColorChack(String s, int i) {		s = textureNameCheck(s);		Object ltb = getTextureBox(s);		if (ltb != null) return getTextureBoxHasColor(ltb, i);		//Modchu_Debug.mDebug("textureColorChack return null !! s="+s+" i="+i);		return false;	}	public static String textureNameCheck(String s) {		if (s != null				&& s.lastIndexOf(ModchuModel_Main.addLmmModelString) > -1) return s;		String s1 = Modchu_TextureManagerBase.defaultModelName;		if (s == null				| (s !=null				&& s.isEmpty())) {			if (s1 != null) {				s = "default_"+s1;				//Modchu_Debug.lDebug("Modchu_Main textureNameCheck default setting.");			}			else Modchu_Debug.lDebug("Modchu_Main textureNameCheck MMM_TextureManager defaultModelName == null !!");		} else {			if (s.indexOf("_") < 0) {				if (s1 != null) s = s+"_"+s1;			}		}		return s;	}	public static Object textureManagerGetArmorTexture(String s, int i, Object itemstack) {		return textureManagerGetArmorTexture(s, i, itemstack, false);	}	public static Object textureManagerGetArmorTexture(String s, int i, Object itemstack, boolean debug) {		if (debug) Modchu_Debug.Debug("textureManagerGetArmorTextureName s="+s+" i="+i+" itemstack != null ? "+(itemstack != null));		s = textureNameCheck(s);		Object ltb = getTextureBox(s);		if (debug) Modchu_Debug.Debug("textureManagerGetArmorTextureName s="+s+" ltb="+ltb);		if (ltb != null) {			if (debug) Modchu_Debug.Debug("textureManagerGetArmorTextureName return="+(((Modchu_TextureBoxBase) ltb).getArmorTextureName(i, itemstack)));			return ((Modchu_TextureBoxBase) ltb).getArmorTextureName(i, itemstack);		}		if (debug) Modchu_Debug.Debug("textureManagerGetArmorTextureName return null !! s="+s+" i="+i+" itemstack="+itemstack);		return null;	}	public static String textureManagerGetNextPackege(String s, int i) {		return textureManagerGetPackege(s, i, 0);	}	public static String textureManagerGetPrevPackege(String s, int i) {		return textureManagerGetPackege(s, i, 1);	}	public static String textureManagerGetPackege(String s, int i, int i1) {		Object ltb = getTextureBox(s);		if (ltb != null) {			ltb = i1 == 0 ? Modchu_Reflect.invokeMethod(Modchu_TextureManagerBase.instance.getClass(), "getNextPackege", new Class[]{ ltb.getClass(), int.class }, Modchu_TextureManagerBase.instance, new Object[]{ ltb, i }) :				Modchu_Reflect.invokeMethod(Modchu_TextureManagerBase.instance.getClass(), "getPrevPackege", new Class[]{ ltb.getClass(), int.class }, Modchu_TextureManagerBase.instance, new Object[]{ ltb, i });			return getTextureBoxTextureName(ltb);		}		Modchu_Debug.mDebug("textureManagerGetPackege return null !! s="+s+" i="+i);		return null;	}	public static String textureManagerGetNextArmorPackege(String s) {		return textureManagerGetArmorPackege(s, 0);	}	public static String textureManagerGetPrevArmorPackege(String s) {		return textureManagerGetArmorPackege(s, 1);	}	public static String textureManagerGetArmorPackege(String s, int i) {		//Modchu_Debug.mDebug("textureManagerGetArmorPackege s="+s+" i="+i);		String s1 = Modchu_TextureManagerBase.defaultModelName;		int index = -1;		String s2 = s;		int i2 = s != null && !s.isEmpty() ? s.lastIndexOf(s1) : -1;		if (i2 > -1) {			s2 = s.substring(0, i2 - 1);		}		//Modchu_Debug.mDebug("textureManagerGetArmorPackege s2="+s2);		index = textureManagerGetArmorPackegeIndex(s2);		if (index == -1) {			index = textureManagerGetArmorPackegeIndex("default");			if (index == -1) {				Modchu_Debug.mDebug("textureManagerGetArmorPackege return index == -1 !!");				return null;			}		}		boolean flag = false;		List<Object> textures = getTextureManagerTextures();		Object ltb = getTextureBox(s);		for (int i1 = 0; i1 < textures.size(); i1++) {			index = i == 0 ? index + 1 : index - 1;			if (index >= textures.size()) index = 0;			if (index < 0) index = textures.size() - 1;			ltb = textures.get(index);			//ltb = i == 0 ? Modchu_Reflect.invokeMethod(MMM_TextureManager, "getNextArmorPackege", new Class[]{MMM_TextureBox}, textureManagerInstance, new Object[]{ltb}) :			//Modchu_Reflect.invokeMethod(MMM_TextureManager, "getPrevArmorPackege", new Class[]{MMM_TextureBox}, textureManagerInstance, new Object[]{ltb});			//Modchu_Debug.mDebug("textureManagerGetArmorPackege index for index="+index+" s="+(String) Modchu_Reflect.getFieldObject(ltb.getClass(), "fileName", ltb));			if (getTextureBoxHasArmor(ltb)) {				s = (String) Modchu_Reflect.getFieldObject(ltb.getClass(), "fileName", ltb);				if (!s.isEmpty()						&& !s.equals(s2)) {					//Modchu_Debug.mDebug("textureManagerGetArmorPackege flag ok.textures.size()="+textures.size());					//Modchu_Debug.mDebug("textureManagerGetArmorPackege flag ok.s="+s);					flag = true;					break;				}			}		}		if (!flag) Modchu_Debug.mDebug("textureManagerGetArmorPackege !flag !!");		i2 = s.lastIndexOf(s1);		if (i2 > -1) s = s.substring(0, i2);		//Modchu_Debug.mDebug("textureManagerGetArmorPackege return s="+s);		return s;	}	private static int textureManagerGetArmorPackegeIndex(String s) {		int index = -1;		List<Object> textures = getTextureManagerTextures();		Object ltb = getTextureBox(s);		for (int i1 = 0; i1 < textures.size(); i1++) {			ltb = textures.get(i1);			//Modchu_Debug.mDebug("textureManagerGetArmorPackegeIndex get "+((String) Modchu_Reflect.getFieldObject(ltb.getClass(), "fileName", ltb)));			if(((String) Modchu_Reflect.getFieldObject(ltb.getClass(), "fileName", ltb)).equals(s)) {				index = i1;				break;			}		}		return index;	}	public static Object[] getTextureBoxModels(Object ltb) {		return ltb != null ? (Object[])Modchu_Reflect.getFieldObject(ltb.getClass(), "models", ltb) : null;	}	public static boolean getTextureBoxHasArmor(Object ltb) {		return ltb != null ? (Boolean)Modchu_Reflect.invokeMethod(ltb.getClass(), "hasArmor", ltb) : false;	}	public static boolean getTextureBoxHasColor(Object ltb, int i) {		return ltb != null ? (Boolean)Modchu_Reflect.invokeMethod(ltb.getClass(), "hasColor", new Class[]{ int.class }, ltb, new Object[]{ i }) : false;	}	public static String getTextureBoxTextureName(Object ltb) {		return ltb != null ? Modchu_CastHelper.String(Modchu_Reflect.getFieldObject(ltb.getClass(), "textureName", ltb)) : null;	}	public static Object getTextureBoxTextureName(Object ltb, int i) {		return ltb != null ? Modchu_Reflect.invokeMethod(ltb.getClass(), "getTextureName", new Class[]{ int.class }, ltb, new Object[]{ i }) : null;	}	public static String getTextureBoxFileName(Object ltb) {		return ltb != null ? (String)Modchu_Reflect.getFieldObject(ltb.getClass(), "fileName", ltb) : null;	}	public static String getTextureBoxPackegeName(Object ltb) {		return ltb != null ? (String)Modchu_Reflect.getFieldObject(ltb.getClass(), "packegeName", ltb) : null;	}	public static List<Object> getTextureManagerTextures() {		return (List<Object>) Modchu_Reflect.getFieldObject(Modchu_TextureManagerBase.instance.getClass(), "textures", Modchu_TextureManagerBase.instance);	}	public static int getTextureManagerTexturesSize() {		return getTextureManagerTextures().size();	}	public static Object getTextureManagerTextures(int i) {		return getTextureManagerTextures().get(i);	}	public static String getModelClassName(String s) {		//Modchu_Debug.mDebug("getModelClassName s="+s);		s = Modchu_Main.lastIndexProcessing(s, "_");		for (Entry<String, MultiModelBaseBiped[]> en : Modchu_TextureManagerBase.instance.modelMap.entrySet()) {			String key = en.getKey();			//Modchu_Debug.mDebug1("getModelClassName key="+key);			if (key.equals(s)) {				MultiModelBaseBiped[] o = en.getValue();				return o[0].getClass().getName();			}		}		return s;	}	public static float[] getArmorModelsSize(Object o) {		Object o1 = Modchu_Reflect.invokeMethod(o.getClass(), "getArmorModelsSize", o, -1);		return o1 != null ? Modchu_CastHelper.FloatArray(o1) : new float[]{ 0.1F, 0.5F };	}	public static int getCapsInt(String s) {		int i = 0;		i = (Integer) Modchu_Reflect.getFieldObject(Modchu_IEntityCapsBase.class, s);		if (i > 0) return i;		i = (Integer) Modchu_Reflect.getFieldObject(Modchu_IEntityCapsBase.class, s);		return i;	}	public static boolean isLMMModel(Object model) {		return model instanceof AbstractModelBase;	}	public static boolean isPFLMModel(Object model) {		return model instanceof MultiModelBaseBiped;	}	public static Object getModelMaster(Object model) {		if (model != null				&& model instanceof Modchu_ModelBase) {			if (((Modchu_ModelBase) model).master instanceof MultiModelBaseBiped) return ((MultiModelBaseBiped) ((Modchu_ModelBase) model).master);		}		return model;	}}