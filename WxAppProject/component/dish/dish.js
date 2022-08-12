Component({
  data: {
    
    color: ['red', 'blue', 'purple', 'orange', 'green']
  },
  properties: {
    dishes: {
      type: Array,
      value:[

      ],
      observer: function (newVal, oldVal, change) {
        console.log("观察者！！", change);
      }
    },
    dishShowType: {
      type: String,
      value: 'none'
    },
  },


  methods: {
    add: function(e) {
      let id = e.currentTarget.dataset.id;
      let dish = this.data.dishes[id];
      this.triggerEvent('add', {index: id, row: dish});
    },
    clickLike: function(e) {
      console.log("comn",e);
      let id = e.currentTarget.dataset.id;
      let dish = this.data.dishes[id];
      console.log("com", id, dish);
      this.triggerEvent('clickLike', {index: id, row: dish});
    },
    clickCollection: function(e) {
      let id = e.currentTarget.dataset.id;
      let dish = this.data.dishes[id];
      console.log("com", id, dish);
      this.triggerEvent('clickCollection', {index: id, row: dish});
    },
  },

  options: {
    multipleSlots: true
  }
})