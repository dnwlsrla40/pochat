
const routes = [
  {
    path: '/',
    component: () => import('layouts/NoLayout.vue'),
    children: [
      { path: '', component: () => import('pages/intro/SignInPage.vue') }
    ]
  },

  {
    path: '/main',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { 
        path: '',
        component: () => import('pages/main/MainPage.vue'),
        children : [
          {
            path : '',
            component: () => import('components/fragments/MainContent.vue')
          },
          {
            path : 'post',
            component: () => import('components/fragments/PostEditor.vue')
          },
        ]
      }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
