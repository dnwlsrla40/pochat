
const routes = [
  {
    path: '/',
    component: () => import('layouts/NoLayout.vue'),
    children: [
      { path: '', component: () => import('pages/intro/SignInPage.vue') },
      { path: 'signup', component: () => import('pages/intro/SignUpPage.vue')}
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
            component: () => import('components/fragments/NoContent.vue')
          },
          {
            name : 'content',
            path : 'content/:chatId/:postId',
            component: () => import('components/fragments/MainContent.vue'),
            //params : {chatId : -1, postId : -1}
          },
          {
            name : 'posteditor',
            path : 'editor/:chatId',
            component: () => import('components/fragments/PostEditor.vue')
          }
        ]
      }
    ]
  },
  {
    path: '/user',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        path : '',
        component: () => import('pages/UserPage.vue')
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
