package boombox.cosmic_critters.entity.custom.Peeper;

import boombox.cosmic_critters.Cosmic_critters;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PeeperEntity extends TameableEntity implements GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.idle");
    protected static final RawAnimation IDLE_TAMED_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.idle_tamed");
    protected static final RawAnimation EAT_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.eat");
    protected static final RawAnimation PEEP_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.peep");
    protected static final RawAnimation BLINK_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.blink");
    protected static final RawAnimation WALK_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.walk");
    protected static final RawAnimation RUN_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.run");
    protected static final RawAnimation SIT_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.sit");
    protected static final RawAnimation TEST_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.test");
    protected static final RawAnimation NO_ANIMATION = RawAnimation.begin().thenLoop("animation.peeper.none");

    public PeeperEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 10)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.WATER_MOVEMENT_EFFICIENCY, 0.1f);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(2, new WanderAroundGoal(this, 1.5F));
        this.goalSelector.add(2, new WanderAroundGoal(this, 4.0F));
        this.goalSelector.add(2, new WanderAroundGoal(this, 5.0F));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 3.0));
        this.goalSelector.add(15, new EscapeDangerGoal(this, 6.0F));
        this.goalSelector.add(10, new FollowOwnerGoal(this, 3.0F, 10.0F, 2.0F));
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    protected AnimationController.AnimationStateHandler idleAnimController = test -> test.setAndContinue(IDLE_ANIMATION);
    protected AnimationController.AnimationStateHandler moveAnimController = test -> {
        PeeperEntity entity = (PeeperEntity) test.animatable();

        if(!test.isMoving()) return test.setAndContinue(entity.isTamed() ? IDLE_TAMED_ANIMATION : IDLE_ANIMATION);

        if(entity.getMovement().lengthSquared() >= 0.02) return test.setAndContinue(RUN_ANIMATION);
        else return test.setAndContinue(WALK_ANIMATION);
    };

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        //controllers.add(new AnimationController<>("Idle", 10, idleAnimController));
        controllers.add(new AnimationController<>("Move", 10, moveAnimController));


    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.GLOW_BERRIES);
    }

    @Override
    protected boolean canTeleportOntoLeaves() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_FOX_AMBIENT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_FOX_DEATH; }

    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(SoundEvents.ENTITY_FOX_HURT);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if(this.getOwner() != null){
           Cosmic_critters.LOGGER.warn(this.getOwner().toString());
        }
        else{
            Cosmic_critters.LOGGER.warn("NO OWNER");
        }
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.isTamed()) {
            if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.eat(player, hand, itemStack);
                FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
                float f = foodComponent != null ? foodComponent.nutrition() : 1.0F;
                this.heal(2.0F * f);
                return ActionResult.SUCCESS;
            }
        }
        else if (!this.getEntityWorld().isClient() && itemStack.isOf(Items.GLOW_BERRIES)) {
            itemStack.decrementUnlessCreative(1, player);
            this.tryTame(player);
            return ActionResult.SUCCESS_SERVER;
        }

        return super.interactMob(player, hand);
    }

    private void tryTame(PlayerEntity player) {
        if (this.random.nextInt(3) == 0) {
            this.setTamedBy(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setSitting(true);
            this.getEntityWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
        } else {
            this.getEntityWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

}
