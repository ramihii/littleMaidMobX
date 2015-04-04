package modchu.lib.lmm.characteristic;import java.util.Map;import java.util.Random;import littleMaidMobX.LittleMaidMobX;import littleMaidMobX.models.IModelCaps;import littleMaidMobX.models.ModelMultiBase;import littleMaidMobX.models.ModelRenderer;import modchu.lib.Modchu_Reflect;import modchu.model.ModchuModel_IEntityCaps;import modchu.model.ModchuModel_ModelDataMaster;import modchu.model.multimodel.base.MultiModelBaseBiped;import net.minecraft.client.model.TextureOffset;import net.minecraft.world.World;public class ModchuLmmXModel extends ModelMultiBase {	public MultiModelBaseBiped master;	public ModchuLmmXModel(Class masterClass) {		init(masterClass, null, (Object[]) null);	}	public ModchuLmmXModel(Class masterClass, float psize) {		init(masterClass, new Class[]{ float.class }, psize);	}	public ModchuLmmXModel(Class masterClass, float psize, float pYOffset) {		init(masterClass, new Class[]{ float.class, float.class }, psize, pYOffset);	}	public ModchuLmmXModel(Class masterClass, float psize, float pYOffset, int par3, int par4) {		init(masterClass, new Class[]{ float.class, float.class, int.class, int.class }, psize, pYOffset, par3, par4);	}	public ModchuLmmXModel(MultiModelBaseBiped masterModel) {		init(masterModel, (Object[]) null);	}	public ModchuLmmXModel(MultiModelBaseBiped masterModel, float psize) {		init(masterModel, psize);	}	public ModchuLmmXModel(MultiModelBaseBiped masterModel, float psize, float pyoffset) {		init(masterModel, psize, pyoffset);	}	public ModchuLmmXModel(MultiModelBaseBiped masterModel, float psize, float pyoffset, int par3, int par4) {		init(masterModel, psize, pyoffset, par3, par4);	}	public void init(Class masterClass, Class[] c, Object... o) {		if (masterClass != null) ;else throw new RuntimeException("Modchu_LmmXModel init masterClass null !!");		Object instance = o != null ? Modchu_Reflect.newInstance(masterClass, c, o) : Modchu_Reflect.newInstance(masterClass);		//Modchu_Debug.mDebug("Modchu_LmmXModel instance="+instance);		master = instance instanceof MultiModelBaseBiped ? (MultiModelBaseBiped) instance : null;		if (master != null) ;else throw new RuntimeException("Modchu_LmmXModel init master null !! masterClass=" + masterClass);	}	public void init(MultiModelBaseBiped masterModel, Object... o) {		if (masterModel != null) ;else throw new RuntimeException("Modchu_LmmXModel init masterModel null !!");		master = masterModel;	}	public String getModelName() {		return LittleMaidMobX.getModelName(master.getClass().getSimpleName(), "_");	}		private ModchuModel_IEntityCaps getModchu_IModelCaps(Object iModelCaps) {		return ModchuModel_ModelDataMaster.instance.getPlayerData(iModelCaps);	}	@Override	public void initModel(float psize, float pyoffset) {		if (master != null) master.initModel(psize, pyoffset);	}	public void superInitModel(float psize, float pyoffset) {	}	@Override	public float getHeight() {		return master != null ? master.getHeight(null) : 1.6F;	}	public float superGetHeight() {		return 1.62F;	}	@Override	public float getWidth() {		return master != null ? master.getWidth(null) : 0.8F;	}	public float superGetWidth() {		return 0.8F;	}	@Override	public float getyOffset() {		return master != null ? master.getYOffset(null) : 1.62F;	}	public float superGetyOffset() {		return 1.62F;	}	@Override	public float getMountedYOffset() {		return master != null ? master.getMountedYOffset(null) : 0.0F;	}	public float superGetMountedYOffset() {		return 0.0F;	}	@Override	public void renderItems(IModelCaps iModelCaps) {		if (master != null) master.renderItems(getModchu_IModelCaps(iModelCaps));	}	public void superRenderItems(Object iModelCaps) {	}	@Override	public void renderFirstPersonHand(IModelCaps iModelCaps) {		if (master != null) master.renderFirstPersonHand(getModchu_IModelCaps(iModelCaps), 0);	}	public void superRenderFirstPersonHand(Object iModelCaps) {	}	@Override	public float[] getArmorModelsSize() {		return master.getArmorModelsSize();	}	@Override	public float getHeight(IModelCaps iModelCaps) {		return master != null ? master.getHeight(getModchu_IModelCaps(iModelCaps)) : super.getHeight(iModelCaps);	}	@Override	public float getWidth(IModelCaps iModelCaps) {		return master != null ? master.getWidth(getModchu_IModelCaps(iModelCaps)) : super.getWidth(iModelCaps);	}	@Override	public float getyOffset(IModelCaps iModelCaps) {		return master != null ? master.getYOffset(getModchu_IModelCaps(iModelCaps)) : super.getyOffset(iModelCaps);	}	@Override	public float getMountedYOffset(IModelCaps iModelCaps) {		return master != null ? master.getMountedYOffset(getModchu_IModelCaps(iModelCaps)) : super.getMountedYOffset(iModelCaps);	}	@Override	public float getLeashOffset(IModelCaps iModelCaps) {		return master != null ? master.getLeashOffset(getModchu_IModelCaps(iModelCaps)) : super.getLeashOffset(iModelCaps);	}	@Override	public String getUsingTexture() {		return master != null ? master.getUsingTexture() : super.getUsingTexture();	}	public String superGetUsingTexture() {		return super.getUsingTexture();	}	@Override	public boolean isItemHolder() {		return master != null ? master.isItemHolder(null) : super.isItemHolder();	}	public boolean superIsItemHolder() {		return super.isItemHolder();	}	@Override	public boolean isItemHolder(IModelCaps iModelCaps) {		return master != null ? master.isItemHolder(getModchu_IModelCaps(iModelCaps)) : super.isItemHolder(iModelCaps);	}	public boolean superIsItemHolder(Object iModelCaps) {		return super.isItemHolder((IModelCaps) iModelCaps);	}	@Override	public void showAllParts() {		if (master != null) master.showAllParts(null);		else super.showAllParts();	}	public void superShowAllParts() {		super.showAllParts();	}	@Override	public void showAllParts(IModelCaps iModelCaps) {		if (master != null) master.showAllParts(getModchu_IModelCaps(iModelCaps));		else super.showAllParts(iModelCaps);	}	public void superShowAllParts(Object iModelCaps) {		super.showAllParts((IModelCaps) iModelCaps);	}	@Override	public int showArmorParts(int parts, int index) {		return master != null ? master.showArmorParts(null, parts, index) : super.showArmorParts(parts, index);	}	public int superShowArmorParts(int parts, int index) {		return super.showArmorParts(parts, index);	}	@Override	public Map<String, Integer> getModelCaps() {		return (Map<String, Integer>) super.getModelCaps();	}	public Map<String, Integer> superGetModelCaps() {		return super.getModelCaps();	}	@Override	public Object getCapsValue(int pIndex, Object... pArg) {		return master != null ? master.getCapsValue(pIndex, pArg) : super.getCapsValue(pIndex, pArg);	}	public Object superGetCapsValue(int pIndex, Object... pArg) {		return super.getCapsValue(pIndex, pArg);	}	@Override	public boolean setCapsValue(int pIndex, Object... pArg) {		return master != null ? master.setCapsValue(pIndex, pArg) : super.setCapsValue(pIndex, pArg);	}	public boolean superSetCapsValue(int pIndex, Object... pArg) {		return super.setCapsValue(pIndex, pArg);	}	@Override	public void render(IModelCaps iModelCaps, float par2, float par3, float ticksExisted, float pheadYaw, float pheadPitch, float par7, boolean pIsRender) {		if (master != null) master.render(getModchu_IModelCaps(iModelCaps), par2, par3, ticksExisted, pheadYaw, pheadPitch, par7, pIsRender);		else super.render(iModelCaps, par2, par3, ticksExisted, pheadYaw, pheadPitch, par7, pIsRender);	}	public void superRender(Object iModelCaps, float par2, float par3, float ticksExisted, float pheadYaw, float pheadPitch, float par7, boolean pIsRender) {		super.render((IModelCaps) iModelCaps, par2, par3, ticksExisted, pheadYaw, pheadPitch, par7, pIsRender);	}	@Override	public void setRotationAngles(float par1, float par2, float pTicksExisted, float pHeadYaw, float pHeadPitch, float par6, IModelCaps iModelCaps) {		if (master != null) master.setRotationAngles(par1, par2, pTicksExisted, pHeadYaw, pHeadPitch, par6, getModchu_IModelCaps(iModelCaps));		else super.setRotationAngles(par1, par2, pTicksExisted, pHeadYaw, pHeadPitch, par6, iModelCaps);	}	public void superSetRotationAngles(float par1, float par2, float pTicksExisted, float pHeadYaw, float pHeadPitch, float par6, Object iModelCaps) {		super.setRotationAngles(par1, par2, pTicksExisted, pHeadYaw, pHeadPitch, par6, (IModelCaps) iModelCaps);	}	@Override	public void setLivingAnimations(IModelCaps iModelCaps, float par2, float par3, float pRenderPartialTicks) {		if (master != null) master.setLivingAnimations(getModchu_IModelCaps(iModelCaps), par2, par3, pRenderPartialTicks);		else super.setLivingAnimations(iModelCaps, par2, par3, pRenderPartialTicks);	}	public void superSetLivingAnimations(Object iModelCaps, float par2, float par3, float pRenderPartialTicks) {		super.setLivingAnimations((IModelCaps) iModelCaps, par2, par3, pRenderPartialTicks);	}	@Override	public ModelRenderer getRandomModelBox(Random random) {		return (ModelRenderer) (master != null ? master.getRandomModelBox(random) : super.getRandomModelBox(random));	}	public ModelRenderer superGetRandomModelBox(Object random) {		return super.getRandomModelBox((Random) random);	}/*	@Override	protected void setTextureOffset(String par1Str, int par2, int par3) {		if (master != null) master.setTextureOffset(par1Str, par2, par3);		else super.setTextureOffset(par1Str, par2, par3);	}	public void superSetTextureOffset(String par1Str, int par2, int par3) {		super.setTextureOffset(par1Str, par2, par3);	}*/	@Override	public TextureOffset getTextureOffset(String par1Str) {		return (TextureOffset) super.getTextureOffset(par1Str);	}	public TextureOffset superGetTextureOffset(String par1Str) {		return super.getTextureOffset(par1Str);	}/*	@Override	public boolean canSpawnHear(World world, int pX, int pY, int pZ) {		return master != null ? master.canSpawnHear(world, pX, pY, pZ) : super.canSpawnHear(world, pX, pY, pZ);	}	public boolean superCanSpawnHear(Object world, int pX, int pY, int pZ) {		return super.canSpawnHear((World) world, pX, pY, pZ);	}*/}