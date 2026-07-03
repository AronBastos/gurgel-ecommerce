# Imagens dos carros

As imagens são referenciadas pelo backend (`DataLoader.java`) como `/cars/<slug>.jpg`
e servidas pelo Angular a partir de `public/`. Proporção alvo **3:2** (ex.: 1200×800);
o `object-fit: cover` acomoda variações. Enquanto um arquivo não existir, a interface
exibe `_placeholder.svg` automaticamente.

## Créditos das fotos (Wikimedia Commons · Creative Commons)

Fotografias históricas usadas apenas para fins ilustrativos/educacionais. Cada uma
pertence ao seu autor e à licença indicada na página de origem no Wikimedia Commons.

| Modelo | Arquivo | Origem (Wikimedia Commons) |
|--------|---------|----------------------------|
| BR-800 | `br-800.jpg` | `File:1991_Gurgel_BR800_SL.jpg` |
| Supermini | `supermini.jpg` | `File:Gurgel_Supermini_BR-SL.jpg` |
| Itaipu | `itaipu.jpg` | `File:Gurgel_Itaipu_E150.jpg` (CC BY 3.0) |
| Xavante | `xavante.jpg` | `File:Gurgel_X12_TR_(1978)_Classic-Days_2022_DSC_0107.jpg` |
| Tocantins | `tocantins.jpg` | `File:Gurgel_X-12_Tocantins.jpg` |
| Carajás | `carajas.jpg` | `File:Gurgel_Carajás_1986.jpg` |
| X-12 | `x-12.jpg` | `File:Gurgel_X-12_TR.jpg` |

Acesse qualquer arquivo em `https://commons.wikimedia.org/wiki/<File:...>` para ver
autor e termos exatos da licença.
