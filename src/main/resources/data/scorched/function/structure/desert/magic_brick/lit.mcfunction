

particle flash ~ ~ ~ 0 0 0 .1 5 force @a[distance=..32]
particle end_rod ~ ~ ~ 0 0 0 .15 15 force @a[distance=..32]
particle end_rod ~ ~ ~ 0 0 0 .3 15 force @a[distance=..32]
particle soul_fire_flame ~ ~ ~ .5 .5 .5 .01 12 force @a[distance=..32]
particle block{block_state:"minecraft:purple_concrete"} ~ ~ ~ .3 .3 .3 0 50 normal

playsound block.glass.break block @a[distance=..32] ~ ~ ~ 2 .8
playsound block.fire.extinguish block @a[distance=..32] ~ ~ ~ 2 .8
playsound item.trident.return block @a[distance=..32] ~ ~ ~ 2 .65

setblock ~ ~1 ~ air destroy

execute as @s[tag=!scorched_des_magic2] run setblock ~ ~ ~ chest{LootTable:"scorched:chest/desert_temple"}

execute as @s[tag=scorched_des_magic2,nbt={Rotation:[90f,0f]}] run setblock ~ ~ ~ chest[facing=west]{LootTable:"scorched:chest/desert_temple2"}
execute as @s[tag=scorched_des_magic2,nbt={Rotation:[0f,0f]}] run setblock ~ ~ ~ chest[facing=south]{LootTable:"scorched:chest/desert_temple2"}
execute as @s[tag=scorched_des_magic2,nbt={Rotation:[-90f,0f]}] run setblock ~ ~ ~ chest[facing=east]{LootTable:"scorched:chest/desert_temple2"}
execute as @s[tag=scorched_des_magic2,nbt={Rotation:[180f,0f]}] run setblock ~ ~ ~ chest[facing=north]{LootTable:"scorched:chest/desert_temple2"}



tag @s add scorched_des_magic_lit_2