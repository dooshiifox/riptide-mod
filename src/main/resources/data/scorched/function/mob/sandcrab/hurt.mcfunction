scoreboard players set @s scorched_HurtTime 1

data modify entity @s[tag=scorched_sandcrab_red] ArmorItems[3].components.minecraft:profile.properties[0].value set value "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGExNmRhMTY4OGQzZTc3MjZjNjA0OGE0Yjc1YmJmNjA3YzA3M2UwNzU3OTRjZjE0YTE4MzNjMGNlMzBhMjRjIn19fQ=="
data modify entity @s[tag=scorched_sandcrab_blue] ArmorItems[3].components.minecraft:profile.properties[0].value set value "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQ5N2FhNjc3MzkzMmY4NTMwZTUxNGQ0ZjkwZGRmOGUyM2ZhODMzNDAxMjlkN2ViMmU5NjMxZWVlNjg5ZjI0YyJ9fX0="
data modify entity @s[tag=scorched_sandcrab_yellow] ArmorItems[3].components.minecraft:profile.properties[0].value set value "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhjZjlkMjU4NWQ4Njc5MjM1NjllMGE3MTE2Yjg3YzdmNTRhZGJmYTM4ODVhOGE5NzZiMzIxYjljZTBmNjRlYiJ9fX0="
data modify entity @s[tag=scorched_sandcrab_magenta] ArmorItems[3].components.minecraft:profile.properties[0].value set value "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIwMDQ5MWIxMjgwZWIwODJhZDg0N2UzZjYzYmQ5NzdiNTkyZTdmYzQ2NTMyMjgxNjlkMWRiM2UwY2Y1YzcyMSJ9fX0="
data modify entity @s[tag=scorched_sandcrab_green] ArmorItems[3].components.minecraft:profile.properties[0].value set value "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWYyNGUwMWQzNGFiNzExMmQ1MGVhNjY4MDZiN2Y5YmQ1YWMwNjRjZGMyMTZiODBiZTQ3MjU0YTFhNzEzYWYxYSJ9fX0="


playsound minecraft:entity.turtle.egg_crack neutral @a[distance=..20] ~ ~ ~ .5 1.5
playsound minecraft:entity.turtle.egg_crack neutral @a[distance=..20] ~ ~ ~ .5 2
playsound minecraft:entity.silverfish.hurt neutral @a[distance=..20] ~ ~ ~ .4 2


scoreboard players set @s scorched_count3 0