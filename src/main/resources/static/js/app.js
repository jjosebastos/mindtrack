document.addEventListener('DOMContentLoaded', () => {
    const appContainer = document.getElementById('app-container');
    const sidebarToggle = document.getElementById('sidebar-toggle');
    const appMain = document.getElementById('app-main');
    const appFooter = document.getElementById('app-footer');

    const navSpans = document.querySelectorAll('.nav-link span');
    const userInfoDiv = document.getElementById('user-info');
    const userOptions = document.getElementById('user-options');
    const logoText = document.getElementById('logo-text');
    const toggleIcon = sidebarToggle.querySelector('i');

    const EXPANDED_WIDTH = '250px';
    const COLLAPSED_WIDTH = '80px';

    appContainer.style.gridTemplateRows = '1fr 40px';
    appContainer.style.gridTemplateColumns = `${EXPANDED_WIDTH} 1fr`;
    appContainer.setAttribute('data-sidebar-expanded', 'true');
    appMain.style.gridColumnStart = 2;
    appFooter.style.gridColumnStart = 2;
    const toggleSidebar = () => {
        const isCurrentlyExpanded = appContainer.getAttribute('data-sidebar-expanded') === 'true';

        if (isCurrentlyExpanded) {
            appContainer.style.gridTemplateColumns = `${COLLAPSED_WIDTH} 1fr`;
            appContainer.setAttribute('data-sidebar-expanded', 'false');
            toggleIcon.classList.add('rotate-180');
            navSpans.forEach(span => {
                span.classList.add('hidden', 'opacity-0');
            });
            userInfoDiv.classList.add('hidden', 'opacity-0');
            userOptions.classList.add('opacity-0');
            logoText.classList.add('opacity-0');

        } else {
            appContainer.style.gridTemplateColumns = `${EXPANDED_WIDTH} 1fr`;
            appContainer.setAttribute('data-sidebar-expanded', 'true');
            toggleIcon.classList.remove('rotate-180');

            navSpans.forEach(span => {
                span.classList.remove('hidden', 'opacity-0');
            });
            userInfoDiv.classList.remove('hidden', 'opacity-0');
            userOptions.classList.remove('opacity-0');
            logoText.classList.remove('opacity-0');
        }
        appMain.style.gridColumnStart = 2;
        appFooter.style.gridColumnStart = 2;
    };
    sidebarToggle.addEventListener('click', toggleSidebar);
});

