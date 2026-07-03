# Gurgel E-commerce — Frontend

SPA em **Angular 20** (componentes standalone + signals) para o catálogo, carrinho e pedidos da Gurgel. Consome a API Spring Boot em `http://localhost:8080/api`.

> Para a documentação completa do projeto (backend + frontend), veja o [README principal](../README.md).

## Requisitos

- Node 20+
- API backend rodando (veja o README principal)

## Desenvolvimento

```bash
npm install
npm start          # ng serve → http://localhost:4200
```

O dev server faz *hot reload* a cada alteração.

> **Nota:** o Angular indexa a pasta `public/` na inicialização. Ao adicionar novos
> arquivos em `public/cars/`, reinicie o `ng serve` para que sejam servidos.

## Build de produção

```bash
npm run build      # gera dist/
```

## Testes

```bash
npm test           # Karma + Jasmine
```

## Estrutura

```
src/
├── index.html            # fontes (Archivo/Inter), meta tags
├── styles.scss           # design tokens (cores, tipografia, dark mode)
└── app/
    ├── app.{ts,html,scss}    # shell: header, footer, router-outlet
    ├── app.config.ts         # providers, LOCALE_ID pt-BR
    ├── app.routes.ts         # rotas
    ├── core/                 # services (HttpClient) + models
    └── pages/
        ├── catalog/          # hero + grid de carros
        ├── cart/             # carrinho + checkout + confirmação
        └── orders/           # histórico de pedidos
public/
└── cars/                 # imagens dos modelos (/cars/<slug>.jpg) + placeholder
```

## Design

Sistema visual inspirado no **modernismo brasileiro** (curvas de Niemeyer, padrões
de Athos Bulcão): paleta verde-mata + laranja-tijolo + ocre em CSS variables, com
**dark mode** automático via `prefers-color-scheme`. Tipografia Archivo (títulos) +
Inter (corpo). Todos os tokens ficam em [`src/styles.scss`](src/styles.scss).
