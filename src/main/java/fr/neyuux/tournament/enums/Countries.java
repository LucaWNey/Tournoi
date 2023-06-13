package fr.neyuux.tournament.enums;

import fr.neyuux.tournament.Tournament;
import fr.neyuux.tournament.TournamentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

public enum Countries {

    ALGERIA("Algérie", "§2§lAlg§4§le§f§lria", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGIzYzYwYjhmNjYxMjg1ZWNkYWU0ZGJkZDQyZWRlYTRhM2I2YzFjMjQ2NzIxZGI3ZDIyOThmMjZlOWFiMWFhNiJ9fX0="),
    ARGENTINA("Argentine", "§b§lArg§f§lent§b§line", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjZkMDMzZGM1ZjY3NWFkNTFiYzA2YzdhMTk0OWMzNWExZDM3ZTQ4YTJlMWMyNzg5YzJjZjdkMzBlYzU4ZjMyYyJ9fX0="),
    AUSTRALIA("Australie", "§c§lA§f§lu§1§lstralie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Y0YWEyYTI0NDc4NGQ0OGIxNTVmZjA0NGI4Y2Y5NmRmNWJkNGU4N2UwMTkyNGE3NWQ2MmE5MjQyYTE2Y2YifX19"),
    BELARUS("Biélorussie", "§c§lBiéloruss§2§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdlNDc2YWUzNTJmNDhlMTZmMThkNzU2ZWIyNTk1N2ViYWVmNDI4ZTZjNGQxNDY2YWU1OTFiMzg0OThlIn19fQ=="),
    BELGIUM("SEUM LAND", "§8§lBe§e§llgi§c§lque", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhlMGUwZGU3MDVkMzY2YmE1OTUwNmIzZmIxYTA0ZmQzZDUxYWZmOTBlNWNmYjRlYmJlYTY2ZWE1OTc2YzBmZiJ9fX0="),
    BRAZIL("Brésil", "§2§lBr§e§lé§1§ls§e§li§2§ll", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTY2OGExZmI2YWY4MWIyMzFiYmNjNGRlNWY3Zjk1ODAzYmJkMTk0ZjU4MjdkYTAyN2ZhNzAzMjFjZjQ3YyJ9fX0="),
    CAMEROON("Cameroun", "§2§lCam§c§ler§e§loun", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRlY2UzNTJiMTc0NWRmZTQ2OWNmNjVlMDYyYWViMDQ5M2MxZDQxNThiYWMwYzhhZDA2YTNkMmY1ZjI3MjkxNSJ9fX0="),
    CANADA("Canada", "§c§lCa§f§lna§c§lda", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjI0MWE2OTdmNmRmYjFjNTdjZGEzMjdiYWE2NzMyYTc4MjhjMzk4YmU0ZWJmZGJkMTY2YzIzMmJjYWUyYiJ9fX0="),
    COLOMBIA("Colombie", "§e§lColo§1§lmb§c§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzcyMTIwYjlmZTc3ZDc3MGIyNTRiMzljN2JlOWIxY2VjY2VmNmFkZmU3YTVjYzY4OWFiMWZkMjNlM2MifX19"),
    CONGO_DR("R.D. du Congo", "§3§lRD §e§ld§c§lu §3§lCongo", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjgxMzg4MTIwYThjM2NhNDViNjgzZTA4MzM5YTFmODJiY2EyMzExYWU2NDdjOGY0ZTZhODI2N2E5MzdhNzBlYyJ9fX0="),
    COSTA_RICA("Costa Rica", "§1§lCosta §c§lRica", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzkxNGVmNTI0MmRkMzRiZWJhZjlkZTVjZjE5OWViODg1NGZjZmY0ZjdjNTg2OWQyMzgyMzMxZDQwYjVlZWE3NCJ9fX0="),
    CROATIA("Croatie", "§c§lCr§f§loat§1§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1MGMwNGVjOGNhYmNlNzFkNzEwM2YzZTllZjRiYjg4MTlmOWYzNjVlYjMzNWE5MTM5OTEyYmMwN2VkNDQ1In19fQ=="),
    DENMARK("Danemark", "§c§lD§f§la§c§lanemark", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTBjMjMwNTVjMzkyNjA2ZjdlNTMxZGFhMjY3NmViZTJlMzQ4OTg4ODEwYzE1ZjE1ZGM1YjM3MzM5OTgyMzIifX19"),
    ECUADOR("Equateur", "§e§lEqua§1§lte§c§lur", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzI4NjliNTVhNDAxYzg3ZmI5M2MzNmEyZDJlNzRlYTRkZDcyMjU0MmFiMGZjM2MwZGJmOTUxMTc3MzI1MTQ1NSJ9fX0="),
    EGYPT("Egypte", "§c§lEg§f§lyp§8§lte", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI2ZTc0MmIzMmYwZjhkYjU5YzA3YjFiY2RkZTZmOGE5M2Y4NWM5MjllNTk4YzdlOTI3M2I5MjExZjJjZTc4In19fQ=="),
    ENGLAND("Angleterre", "§f§lAngl§c§let§f§lerre", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmVlNWM4NTBhZmJiN2Q4ODQzMjY1YTE0NjIxMWFjOWM2MTVmNzMzZGNjNWE4ZTIxOTBlNWMyNDdkZWEzMiJ9fX0="),
    ESTONIA("Estonie", "§9§lEs§8§lto§f§lnie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhjY2U2NjI3ZTRkYmE5NjY0MGRhN2JiY2E4Y2Q0M2E3ODM0OTUxNjdiYWYyODM0YWE5OTExNzAxOGFkZiJ9fX0="),
    FRANCE("France", "§9§lFr§f§lan§c§lce", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkwMzM0OWZhNDViZGQ4NzEyNmQ5Y2QzYzZjMGFiYmE3ZGJkNmY1NmZiOGQ3ODcwMTg3M2ExZTdjOGVlMzNjZiJ9fX0="),
    GERMANY("Allemagne", "§8§lAll§c§lema§e§lgne", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ=="),
    GHANA("Ghana", "§c§lGh§e§la§2§lna", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhYjM2NmU5MDQ4ZGI1NjQ3MDAzYzNmOTc3Zjc1MzczZDc2NzViNzk3Nzk0Y2UyYzkxOTgwMGVjNDQ3YzY3NyJ9fX0="),
    GREECE("Grèce", "§3§lG§f§lr§3§lè§f§lc§3§le", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTUxNGRlNmRkMmI3NjgyYjFkM2ViY2QxMDI5MWFlMWYwMjFlMzAxMmI1YzhiZWZmZWI3NWIxODE5ZWI0MjU5ZCJ9fX0="),
    HUNGARY("Hongrie", "§c§lHo§f§lngr§2§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGE5YzNjNGI2YzUwMzEzMzJkZDJiZmVjZTVlMzFlOTk5ZjhkZWZmNTU0NzQwNjVjYzg2OTkzZDdiZGNkYmQwIn19fQ=="),
    INDIA("Inde", "§6§lI§f§ln§2§lde", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmY4OWU0NmYxZDU0NTRjOTY3Njc2ZWZiNDk3N2E4NGExOGQwMDg0MzI2Yzc2YzdiZmM0ZTc4NDNiYjFhOTAxIn19fQ=="),
    IRAN("Iran", "§2§lI§f§lr§4§lan", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWNkOWJhZGYxOTcyNTgzYjY2M2I0NGIxZTAyNzI1NWRlOGYyNzVhYTFlODlkZWZjZjc3NzgyYmE2ZmNjNjUyIn19fQ=="),
    ITALY("Italie", "§3§lIt§f§lal§c§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVjZTg5MjIzZmE0MmZlMDZhZDY1ZDhkNDRjYTQxMmFlODk5YzgzMTMwOWQ2ODkyNGRmZTBkMTQyZmRiZWVhNCJ9fX0="),
    IVORY_COAST("Cote d'Ivoire", "§6§lCote §f§ld'§2§lIvoire", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5NjZjOWJmNDJjNTFiZTA5YWZlMTVmY2M2YzNmZWFkYjAwY2FjNzJkOTYyZjZmZDBlYmZmYTczM2ViMjQwOCJ9fX0="),
    JAPAN("Japon", "§f§lJa§c§lp§f§lon", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDZjMmNhNzIzODY2NmFlMWI5ZGQ5ZGFhM2Q0ZmM4MjlkYjIyNjA5ZmI1NjkzMTJkZWMxZmIwYzhkNmRkNmMxZCJ9fX0="),
    LEBANON("Liban", "§c§lL§f§li§2§lb§f§lb§c§ln", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzIwNDE5ZWYyYzY2ZTI5YTE0MmVmMzQxYzBlY2NmNjk3YWNhODZlYzA5YzZkMThmNjA0M2E1M2NhMzBjZjJkMSJ9fX0="),
    MADAGASCAR("Madagascar", "§f§lMada§c§lgas§2§lcar", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODMxZDM5NGNjMzc3ZGMzNjRjMTBkODc3ZmMzYWMxYmYwNjk1MmQxOThmMGEyNTQ5NzRlODgzY2Q3OGUwIn19fQ=="),
    MEXICO("Mexique", "§2§lMe§f§lx§6§li§f§lq§c§lue", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBlMDgyZmUxYmM2ZWY4YmVkZmI1MzZmYjJlMDQxZGNlZmViZjRkYWM1YTMzNDJjMGMxMzdmMTc0NDFkYjVhNCJ9fX0="),
    MOROCCO("Maroc", "§c§lMa§2§lr§c§loc", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzUzZWM2YTc0Mjk4MjhjN2E4ZjgxZTFmOWZlNmUxNmZiMTZiNDMzYjRmOGU5NDUzNWI5OGE5NmQ2MjFmNCJ9fX0="),
    NETHERLANDS("Pays-Bas", "§c§lPay§f§ls-§1§lBas", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzIzY2YyMTBlZGVhMzk2ZjJmNWRmYmNlZDY5ODQ4NDM0ZjkzNDA0ZWVmZWFiZjU0YjIzYzA3M2IwOTBhZGYifX19"),
    NIGER("Nigger", "§6§lNi§f§lg§6§lg§a§ler", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDE0M2Q3OTZhZTU2MjlmNGRlNzE2ZjhiZmIzZTMyYWIyODc2MmJmY2JmOGM2NzA2ZDFiYWU3MGE1YTNjNGZhIn19fQ=="),
    NORTH_KOREA("Corée du Nord", "§c§lCorée §f§ldu §1§lNord", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTk2OWQxMjY2MmZhZWJmYWNhNmY0YjA0NDJmY2IyNTFmZDYwYjYxYTlmY2RjZWVhMmJkYzIxZTAyNWViMjEifX19"),
    NORWAY("Norvège", "§c§lNo§f§lr§1§lè§f§lg§c§le", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmRhMDQ4YmMxNTNiMzg0NjdlNzZhMzM0N2YzODM5Njg2MGE4YmM2ODYwMzkzMWU5MWY3YWY1OGJlYzU3MzgzZCJ9fX0="),
    PERU("Pérou", "§c§lPé§f§lr§c§lou", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjRkMDNiZDQ0MTBiYWJkYzY4MjQ5M2IzYzJiYmEyNmU3MzBlNmJjNjU4ZDM4ODhlNzliZjcxMmY4NTMifX19"),
    POLAND("Pologne", "§f§lPolo§c§lgne", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTIxYjJhZjhkMjMyMjI4MmZjZTRhMWFhNGYyNTdhNTJiNjhlMjdlYjMzNGY0YTE4MWZkOTc2YmFlNmQ4ZWIifX19"),
    PORTUGAL("Portugal", "§2§lPor§e§lt§c§lugal", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWJkNTFmNDY5M2FmMTc0ZTZmZTE5NzkyMzNkMjNhNDBiYjk4NzM5OGUzODkxNjY1ZmFmZDJiYTU2N2I1YTUzYSJ9fX0="),
    QATAR("Qatar", "§f§lQa§5§ltar", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmExY2VlYTBmY2FjOTIwMTQ1ZTY5YmQ5MTAwMTIyODJkNGJlZDQ0ZWMzMGE0NjQxYTFjNjU5NmVlNTU2ZDI0ZCJ9fX0="),
    RUSSIA("Russie", "§f§lRu§1§lss§c§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZlYWZlZjk4MGQ2MTE3ZGFiZTg5ODJhYzRiNDUwOTg4N2UyYzQ2MjFmNmE4ZmU1YzliNzM1YTgzZDc3NWFkIn19fQ=="),
    SAUDI_ARABIA("Arabie Saoudite", "§2§lArabie Saoudite", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTRiZTc1OWE5Y2Y3ZjBhMTlhN2U4ZTYyZjIzNzg5YWQxZDIxY2ViYWUzOGFmOWQ5NTQxNjc2YTNkYjAwMTU3MiJ9fX0="),
    SENEGAL("Sénégal", "§2§lSén§e§lén§c§lal", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZmNWUyOTdiOTcyOTY1ZTMyZDgyMzQxYTM2ZGQ5OTk1YTI1YzljYzc5YjA1NTMxYzRiZDdhNjEzNGQyZDc1In19fQ=="),
    SERBIA("Serbie", "§c§lSe§1§lrb§f§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzY0NjExNjVlNDhiODZjNTZiYjk4ZjQ4YjIwMWFlZjA1YTMwYzkxNGU5MGY0NTE1ZjA1MjE5YzY4MjdlN2UxZCJ9fX0="),
    SOUTH_KOREA("Corée du Sud", "§f§lCorée §9§ldu §c§lSud", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMxYmU1ZjEyZjQ1ZTQxM2VkYTU2ZjNkZTk0ZTA4ZDkwZWRlOGUzMzljN2IxZThmMzI3OTczOTBlOWE1ZiJ9fX0="),
    SPAIN("Espagne", "§c§lEs§e§lpag§c§lne", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0="),
    SWEDEN("Suède", "§3§lS§e§luè§3§lde", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q4NjI0MmIwZDk3ZWNlOTk5NDY2MGYzOTc0ZDcyZGY3Yjg4N2Y2MzBhNDUzMGRhZGM1YjFhYjdjMjEzNGFlYyJ9fX0="),
    SWITZERLAND("Suisse", "§c§lSu§f§lis§c§lse", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTcyZmZhYmM0MGEzYTgyYTlkYmIwODEwMzhmOTgzNDcwN2I4YTdhNTk1ZTQ3ZDYzZTRjN2E3ZTI0YTZjODgyOSJ9fX0="),
    TUNISIA("Tunisie", "§c§lTun§f§lis§c§lie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzQ1OTU5Y2UzZGIxNzcyMWY3ODJmNzZlMzNmOGQ5NmZlNDRlNDllYTk3M2JmNmQxZTcxM2E3YjljMGQ3NTNmNSJ9fX0="),
    TURKEY("Turquie", "§c§lTu§f§lrq§c§luie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTg1MmI5YWJhMzQ4MjM0ODUxNGMxMDM0ZDBhZmZlNzM1NDVjOWRlNjc5YWU0NjQ3Zjk5NTYyYjVlNWY0N2QwOSJ9fX0="),
    UKRAINE("Ukraine", "§9§lUkra§e§line", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjhiOWY1MmUzNmFhNWM3Y2FhYTFlN2YyNmVhOTdlMjhmNjM1ZThlYWM5YWVmNzRjZWM5N2Y0NjVmNWE2YjUxIn19fQ=="),
    UNITED_STATES("Etats-Unis","§f§lEtats§1§l-§c§lUnis", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNhYzk3NzRkYTEyMTcyNDg1MzJjZTE0N2Y3ODMxZjY3YTEyZmRjY2ExY2YwY2I0YjM4NDhkZTZiYzk0YjQifX19"),
    URUGUAY("Uruguay", "§e§lU§f§lrug§1§luay", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg0NDA1OTdjNGJjMmFhZDYwMGE1NDYwNGRjN2IxZmI3NzEzNDNlMDIyZTZhMmUwMjJmOTBlNDBjYzI1ZjlmOCJ9fX0="),
    VIETNAM("Vietnam", "§c§lVie§e§lt§c§lnam", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGE1N2I5ZDdkZDA0MTY5NDc4Y2ZkYjhkMGI2ZmQwYjhjODJiNjU2NmJiMjgzNzFlZTlhN2M3YzE2NzFhZDBiYiJ9fX0="),
    WALES("Pays de Galles", "§f§lPays §4§lde §2§lGalles", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEzMGU4ZjAwNWEyZDVmZmEwMDAyYzEwMDMyNzM3ZWIxODRkNGIyMzM4MjNlMmJlYWE3ZGViYTBkN2Q1ZmEwZSJ9fX0=");

    Countries(String name, String displayName, String minecraftHeadsValue) {
        this.name = name;
        this.displayName = displayName;
        this.minecraftHeadsValue = minecraftHeadsValue;
    }

    private final String name;
    private final String displayName;
    private final String minecraftHeadsValue;

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPrefix() {
        String prefix = getDisplayName() + " §f";
        if (prefix.length() > 32) throw new StringIndexOutOfBoundsException("le displayname de " + getName() + " doit pas depasser 29 caracteres.");
        return prefix;
    }

    public ItemStack getFlagHeadItemStack() {
        ItemStack item = new ItemStack(Material.SKULL_ITEM);
        item.setDurability((short) 3);

        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();

        itemMeta.setDisplayName(this.getDisplayName());
        itemMeta.setLore(Arrays.asList("", "§7>>Clique pour choisir"));

        item.setItemMeta(itemMeta);
        UUID hashAsId = new UUID(this.minecraftHeadsValue.hashCode(), this.minecraftHeadsValue.hashCode());

        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + this.minecraftHeadsValue + "\"}]}}}"
        );
    }

    public boolean isAvailable() {
        Tournament tournament = TournamentPlugin.getInstance().getSelectedTournament();
        if (tournament == null) return true;

        return tournament.getYamlConfiguration().getIntegerList("usedcountries").contains(this.ordinal());
    }


    public static Countries getFromDisplayName(String displayName) {
        return Arrays.stream(Countries.values()).filter(country -> country.getDisplayName().equals(displayName)).findFirst().orElse(null);
    }
}
