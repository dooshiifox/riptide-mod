

scoreboard players set @s[predicate=!scorched:condition/creative_spec,nbt=!{Invulnerable:1b}] smithed.damage 1
function #smithed.damage:entity/apply

#scoreboard players set @s[predicate=!scorched:condition/creative_spec,nbt=!{Invulnerable:1b}] spellbound_damage 1



execute as @s[type=player,predicate=!scorched:condition/creative_spec] run playsound entity.player.hurt player @a[distance=..32] ~ ~ ~ 1 1