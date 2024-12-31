<template>
  <nav>
    <ul>
      <li><a>首页</a></li>
      <li><a><img src=""></a></li>
    </ul>
  </nav>
</template>

<style scoped>
nav{
  background-color: limegreen;
}
ul{
  display: flex;
  list-style: none;
  justify-content: right;
}
ul a{

  display: block;
  height: 50px;
  line-height: 50px;
  text-align: center;
  transition: 1s all;
}
ul a:hover{
  background-color: green;
}
</style>

<script setup>
</script>