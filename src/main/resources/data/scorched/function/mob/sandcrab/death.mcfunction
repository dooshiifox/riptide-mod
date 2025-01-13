scoreboard players set @s scorched_HurtTime 1

data modify entity @s[tag=scorched_sandcrab_red] ArmorItems[3].tag.SkullOwner set value {Id:[I;-660542572,427182566,-1646694551,1503222690],Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGExNmRhMTY4OGQzZTc3MjZjNjA0OGE0Yjc1YmJmNjA3YzA3M2UwNzU3OTRjZjE0YTE4MzNjMGNlMzBhMjRjIn19fQ=="}]}}
data modify entity @s[tag=scorched_sandcrab_blue] ArmorItems[3].tag.SkullOwner set value {Id:[I;973273790,192432372,-1874912085,-1333082831],Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQ5N2FhNjc3MzkzMmY4NTMwZTUxNGQ0ZjkwZGRmOGUyM2ZhODMzNDAxMjlkN2ViMmU5NjMxZWVlNjg5ZjI0YyJ9fX0="}]}}
data modify entity @s[tag=scorched_sandcrab_yellow] ArmorItems[3].tag.SkullOwner set value {Id:[I;-530104706,-1499708210,-1610094621,-734989864],Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhjZjlkMjU4NWQ4Njc5MjM1NjllMGE3MTE2Yjg3YzdmNTRhZGJmYTM4ODVhOGE5NzZiMzIxYjljZTBmNjRlYiJ9fX0="}]}}
data modify entity @s[tag=scorched_sandcrab_magenta] ArmorItems[3].tag.SkullOwner set value {Id:[I;-1757634806,-1694481157,-1856108972,-9094136],Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIwMDQ5MWIxMjgwZWIwODJhZDg0N2UzZjYzYmQ5NzdiNTkyZTdmYzQ2NTMyMjgxNjlkMWRiM2UwY2Y1YzcyMSJ9fX0="}]}}
data modify entity @s[tag=scorched_sandcrab_green] ArmorItems[3].tag.SkullOwner set value {Id:[I;766443791,-790739474,-2097871136,-172504373],Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWYyNGUwMWQzNGFiNzExMmQ1MGVhNjY4MDZiN2Y5YmQ1YWMwNjRjZGMyMTZiODBiZTQ3MjU0YTFhNzEzYWYxYSJ9fX0="}]}}


playsound minecraft:entity.turtle.egg_break neutral @a[distance=..20] ~ ~ ~ .5 1.5
playsound minecraft:entity.turtle.egg_break neutral @a[distance=..20] ~ ~ ~ .5 2
playsound minecraft:entity.silverfish.death neutral @a[distance=..20] ~ ~ ~ .4 2
kill @s[type=zombie]