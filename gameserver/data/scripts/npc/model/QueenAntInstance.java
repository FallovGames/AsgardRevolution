package npc.model;

import l2ar.commons.util.Rnd;
import l2ar.gameserver.data.xml.holder.NpcTemplateHolder;
import l2ar.gameserver.model.Creature;
import l2ar.gameserver.model.SimpleSpawner;
import l2ar.gameserver.model.instances.BossInstance;
import l2ar.gameserver.model.instances.MinionInstance;
import l2ar.gameserver.model.instances.NpcInstance;
import l2ar.gameserver.network.lineage2.serverpackets.PlaySound;
import l2ar.gameserver.network.lineage2.serverpackets.PlaySound.Type;
import l2ar.gameserver.scripts.Functions;
import l2ar.gameserver.templates.npc.NpcTemplate;
import l2ar.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.List;

public class QueenAntInstance extends BossInstance {
    private static final int Queen_Ant_Larva = 29002;
    private final List<SimpleSpawner> _spawns;
    private NpcInstance Larva;

    public QueenAntInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
        _spawns = new ArrayList<>();
        Larva = null;
    }

    public NpcInstance getLarva() {
        if (Larva == null) {
            Larva = SpawnNPC(29002, new Location(-21600, 179482, -5846, Rnd.get(0, 65535)));
        }
        return Larva;
    }

    @Override
    protected int getKilledInterval(final MinionInstance minion) {
        return (minion.getNpcId() == 29003) ? 130000 : 130000;
    }

    @Override
    protected void onDeath(final Creature killer) {
        broadcastPacketToOthers(new PlaySound(Type.MUSIC, "BS02_D", 1, 0, getLoc()));
        Functions.deSpawnNPCs(_spawns);
        Larva = null;
        super.onDeath(killer);
    }

    @Override
    protected void onSpawn() {
        super.onSpawn();
        getLarva();
        broadcastPacketToOthers(new PlaySound(Type.MUSIC, "BS01_A", 1, 0, getLoc()));
    }

    private NpcInstance SpawnNPC(final int npcId, final Location loc) {
        final NpcTemplate template = NpcTemplateHolder.getInstance().getTemplate(npcId);
        if (template == null) {
            System.out.println("WARNING! template is null for npc: " + npcId);
            Thread.dumpStack();
            return null;
        }
        try {
            final SimpleSpawner sp = new SimpleSpawner(template);
            sp.setLoc(loc);
            sp.setAmount(1);
            sp.setRespawnDelay(0);
            _spawns.add(sp);
            return sp.spawnOne();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
