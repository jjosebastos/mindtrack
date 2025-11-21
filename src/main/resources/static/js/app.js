document.addEventListener('DOMContentLoaded', () => {
    console.log("App.js carregado!"); // Teste se o arquivo carregou

    const sidebarWrapper = document.getElementById('sidebar-wrapper');
    const sidebarToggle = document.getElementById('sidebar-toggle');
    const sidebarTexts = document.querySelectorAll('.sidebar-text');

    // Debug: Verifica se encontrou os elementos
    if (!sidebarWrapper) console.error("ERRO: Não encontrei o elemento com id='sidebar-wrapper' no base.html");
    if (!sidebarToggle) console.error("ERRO: Não encontrei o elemento com id='sidebar-toggle' no sidebar.html");

    if (sidebarToggle && sidebarWrapper) {
        sidebarToggle.addEventListener('click', (e) => {
            e.preventDefault(); // Previne comportamentos estranhos de botão
            console.log("Botão clicado!");

            const isExpanded = sidebarWrapper.classList.contains('w-64');

            if (isExpanded) {
                // FECHAR
                console.log("Fechando sidebar...");
                sidebarWrapper.classList.replace('w-64', 'w-20');
                sidebarTexts.forEach(text => text.classList.add('hidden'));
            } else {
                // ABRIR
                console.log("Abrindo sidebar...");
                sidebarWrapper.classList.replace('w-20', 'w-64');
                // Pequeno delay para suavidade
                setTimeout(() => {
                    sidebarTexts.forEach(text => text.classList.remove('hidden'));
                }, 150);
            }
        });
    }
});

