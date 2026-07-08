package com.giaxasidis.frostshield.checks;

import com.giaxasidis.frostshield.checks.movement.*;
import com.giaxasidis.frostshield.checks.combat.*;
import com.giaxasidis.frostshield.checks.packet.*;
import com.giaxasidis.frostshield.checks.world.*;
import com.giaxasidis.frostshield.checks.paranormal.*;
import java.util.ArrayList;
import java.util.List;

public class CheckManager {
    private final List<Check> checks = new ArrayList<>();

    public CheckManager() {
        checks.add(new Speed());
        checks.add(new Fly());
        checks.add(new NoFall());
        checks.add(new NoFallDamage());
        checks.add(new Jesus());
        checks.add(new Step());
        checks.add(new Velocity());
        checks.add(new Spider());
        checks.add(new Glide());
        checks.add(new HighJump());
        checks.add(new WaterWalk());
        checks.add(new Prediction());
        checks.add(new NoSlowdown());
        checks.add(new GroundSpoof());
        checks.add(new AirJump());
        checks.add(new StairsSpeed());
        
        checks.add(new Reach());
        checks.add(new KillAura());
        checks.add(new AutoClicker());
        checks.add(new AimAssist());
        checks.add(new HitBox());
        checks.add(new AntiKnockback());
        checks.add(new Criticals());
        checks.add(new TriggerBot());
        checks.add(new NoSwing());
        checks.add(new AutoArmor());
        checks.add(new AutoTotem());
        checks.add(new FastEat());
        checks.add(new NoFriendDamage());
        
        checks.add(new Timer());
        checks.add(new BadPackets());
        checks.add(new Blink());
        checks.add(new PacketOrder());
        checks.add(new FastLadder());
        checks.add(new InvalidMove());
        checks.add(new InventoryMove());
        
        checks.add(new Nuker());
        checks.add(new FastBreak());
        checks.add(new FastPlace());
        checks.add(new Scaffold());
        checks.add(new Phase());
        checks.add(new GhostHand());
        checks.add(new BedrockBreaker());
        checks.add(new IllegalStack());
        checks.add(new ChestStealer());
        checks.add(new AutoTool());
        checks.add(new IllegalBlockPlace());
        checks.add(new BlockBreakMonitor());
        
        checks.add(new ESP());
        checks.add(new ChestAura());
        checks.add(new BoatFly());
        checks.add(new ElytraFly());
        checks.add(new AntiVoid());
        checks.add(new FastClimb());
        checks.add(new MultiAura());
        checks.add(new HitBoxExpansion());
        checks.add(new ReachBlock());
        checks.add(new JumpReset());
        checks.add(new AutoRespawn());
        checks.add(new Freecam());
        checks.add(new AntiHunger());
    }

    public List<Check> getChecks() { return checks; }
    public int getTotalChecks() { return checks.size(); }
}






