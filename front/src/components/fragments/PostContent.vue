<template>
  <q-card flat bordered square class="my-card bg-grey-1">
    <q-card-section>
      <div class="row items-center no-wrap">
        <div class="col">
          <div class="text-h6">포스트 제목</div>
          <div class="text-subtitle2">by 누구누구</div>
        </div>

        <div class="col-auto">
          <q-btn color="grey-7" round flat icon="more_vert">
            <q-menu cover auto-close>
              <q-list>
                <q-item clickable>
                  <q-item-section>수정하기</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section>삭제하기</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>
      </div>
    </q-card-section>

    <q-separator />

    <q-card-section>
      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce a nibh tincidunt, luctus ligula at, tincidunt mauris. Quisque at nunc id eros iaculis facilisis. Maecenas eget nibh metus. Morbi congue elit erat, quis ornare lectus semper nec. Fusce tellus urna, consequat quis convallis ut, posuere a elit. Fusce feugiat dolor lacus, a porta mauris porttitor id. Praesent aliquet vitae elit a rhoncus.

Aenean at lorem et velit vehicula tristique. Praesent finibus ornare metus ornare iaculis. Fusce id volutpat diam, quis tempor est. Maecenas nunc leo, consectetur sit amet scelerisque suscipit, varius sed elit. In maximus nulla tortor, sit amet ultricies lectus fermentum quis. Mauris tristique suscipit nibh in condimentum. Aliquam at justo molestie, bibendum ex quis, porta risus. Donec lacinia est eros, et pellentesque arcu dictum quis. Aliquam ac fermentum nisl. Morbi vel tortor non tellus porttitor vulputate nec ut leo. Aliquam a maximus arcu. Nunc cursus augue ante, pulvinar laoreet tortor vulputate ac. Nunc pharetra, lectus vel facilisis laoreet, dui odio imperdiet lorem, nec porta risus nibh nec orci.

Vivamus venenatis et odio eget condimentum. Nullam arcu augue, bibendum ut fermentum at, finibus et odio. Proin feugiat, magna id porttitor placerat, ante est interdum elit, id faucibus sapien risus sed dui. Ut placerat commodo lacinia. Aliquam ac ultricies sem. Aenean feugiat accumsan nunc, ut porta purus semper vel. Vestibulum eget mauris vitae dui vehicula ultrices. Nullam eu suscipit sapien, nec maximus lacus. Ut at nisl sodales enim porta lobortis. Pellentesque ante nulla, pharetra in viverra vel, lobortis mollis mi. Sed a sem at nibh hendrerit tincidunt lobortis sit amet tortor. Suspendisse vestibulum lacinia nunc, at elementum sapien convallis feugiat. Donec fringilla aliquet semper. Nulla vitae mollis ligula. Aliquam molestie vel massa ut porta.

Morbi porttitor, ex et ornare interdum, lectus libero auctor sem, nec auctor arcu orci at turpis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. In laoreet metus vel urna condimentum, at venenatis sem pulvinar. Nunc risus lectus, porta vel turpis a, tristique efficitur dolor. Nam sodales ante et lorem sodales, id fermentum augue ultricies. Proin sodales velit libero, vel vestibulum purus tincidunt laoreet. Maecenas at posuere massa, at mollis ipsum. Nunc luctus, orci id accumsan dignissim, neque mi lacinia augue, eget cursus massa massa eget orci. Aenean porta quam eu libero luctus, quis tincidunt massa pharetra. Vivamus placerat rhoncus leo, non fermentum ex sollicitudin id. Maecenas euismod velit non nibh pellentesque, non interdum metus egestas. Phasellus porttitor rutrum nunc, ac tincidunt quam tempus ac.

Fusce bibendum risus id lorem iaculis venenatis. Ut at pretium ipsum. Nunc et auctor lorem. Nulla aliquet bibendum est vel scelerisque. Duis efficitur tristique maximus. Quisque tempus pulvinar lectus, id dignissim est eleifend vel. Sed eu justo velit. Curabitur porttitor purus at lectus blandit luctus. Fusce nec imperdiet tellus. Donec felis lectus, faucibus consectetur laoreet vitae, dictum a mauris. Sed ligula nisi, aliquet non lorem ac, cursus sollicitudin sapien.
    </q-card-section>

  </q-card>
</template>

<script>
export default {
  name: 'PostContent'
}
</script>
