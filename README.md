# 🧠 MindTrack -- Monitoramento Inteligente de Humor

### Projeto desenvolvido por: **José Bezerra Bastos Neto, Nicolas Dobbeck e Thiago Henry**

MindTrack é uma solução de monitoramento emocional que permite que
colaboradores registrem seus estados de humor ao longo do tempo.
Utilizando IA generativa, a plataforma identifica padrões, gera
relatórios inteligentes e envia alertas quando detecta emoções
sensíveis, como **tristeza, raiva ou exaustão**.

------------------------------------------------------------------------

## 🚀 Tecnologias Utilizadas

-   **Java 17**
-   **PostgreSQL**
-   **Flyway Migrations**
-   **Thymeleaf**
-   **TailwindCSS**
-   **Docker Compose**
-   **Render (Deploy em Produção)**\
-   **IA Generativa para relatórios e insights**

------------------------------------------------------------------------

## 🎯 Objetivo da Solução

A MindTrack tem como propósito promover o bem-estar corporativo através
de:

-   ✔ Registro diário de humor\
-   ✔ Detecção de sentimentos negativos\
-   ✔ Relatórios automáticos gerados por IA\
-   ✔ Acompanhamento diário, semanal e trimestral\
-   ✔ Geração de insights personalizados\
-   ✔ Suporte à gestão de clima organizacional

A solução auxilia empresas e equipes a compreender melhor o bem-estar de
seus colaboradores, detectando sinais precoces de burnout, queda de
engajamento e outros fatores emocionais importantes no ambiente de
trabalho.

------------------------------------------------------------------------

## 📦 Instalação e execução (Docker)

``` bash
docker compose up --build
```

A aplicação será iniciada automaticamente com o backend Java, banco
PostgreSQL e migrações via Flyway.

------------------------------------------------------------------------

## 🌐 Deploy

A aplicação está hospedada na plataforma **Render**, com build
automatizado e ambiente configurado para produção.

[Link](https://mindtrack-55mc.onrender.com)

------------------------------------------------------------------------

## 📁 Estrutura simplificada do projeto

    /src
      /main
        /java
          /controller
          /service
          /repository
          /model
        /resources
          templates/
          static/
          db/migration/

------------------------------------------------------------------------

#
