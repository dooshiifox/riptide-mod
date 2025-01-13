

execute unless score load_message refresh_settings matches ..0 run tellraw @a [{"translate": "Successfully loaded ","color": "gray"},{"translate": "Scorched v1.1.4b","color": "green"}]

scoreboard objectives add refresh_settings dummy

### Pack Installed

scoreboard objectives add splatus.packs_installed dummy
scoreboard players set Scorched splatus.packs_installed 1 

###

team add scorched_mob
team modify scorched_mob nametagVisibility never

scoreboard objectives add scorched_settings dummy
scoreboard objectives add scorched_count dummy
scoreboard objectives add scorched_count2 dummy
scoreboard objectives add scorched_count3 dummy
scoreboard objectives add scorched_clock dummy
scoreboard objectives add scorched_constants dummy
scoreboard objectives add scorched_poisoned dummy
scoreboard objectives add scorched_death deathCount



#settings
execute unless score mobs scorched_settings matches -2147483648.. run scoreboard players set mobs scorched_settings 1
execute unless score structure_wells scorched_settings matches -2147483648.. run scoreboard players set structure_wells scorched_settings 1
execute unless score postgen scorched_settings matches -2147483648.. run scoreboard players set postgen scorched_settings 1




scoreboard objectives add scorched_HurtTime dummy
scoreboard objectives add scorched_health dummy
scoreboard objectives add scorched_randomizer dummy


scoreboard players set 1 scorched_constants 1
scoreboard players set 2 scorched_constants 2
scoreboard players set 3 scorched_constants 3
scoreboard players set 4 scorched_constants 4
scoreboard players set 5 scorched_constants 5
scoreboard players set 6 scorched_constants 6
scoreboard players set 7 scorched_constants 7
scoreboard players set 8 scorched_constants 8
scoreboard players set 9 scorched_constants 9
scoreboard players set 10 scorched_constants 10
scoreboard players set 16 scorched_constants 16
scoreboard players set 24 scorched_constants 24
scoreboard players set 60 scorched_constants 60
scoreboard players set 1000 scorched_constants 1000
scoreboard players set 16666 scorched_constants 16666