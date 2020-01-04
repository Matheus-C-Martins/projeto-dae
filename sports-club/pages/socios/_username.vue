<!-- eslint-disable -->
<template>
  <v-app id="inspier">
    <v-card>
      <v-card-title style="padding-bottom: 0px" class="headline"> Detalhes do Sócio </v-card-title>
      <v-card-text class="pa-0">
        <v-form align="center">
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Username: </h6> {{socio.username}}
              </v-card>
            </v-col>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Nome: </h6> {{socio.nome}}
              </v-card>
            </v-col>
          </v-row>
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Email: </h6> {{socio.email}}
              </v-card>
            </v-col>
          </v-row>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn color='primary' dark @click='back'> Voltar </v-btn>
      </v-card-actions>
    </v-card>
  </v-app>
</template>

<script>
/* eslint-disable */
export default {
  data() {
    return {
      socio: {},
    };
  },
  created() {
    this.$axios.$get(`/api/socios/${this.username}`).then(socio => (this.socio = socio || {}));
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
  },
  methods: {      
    back () {
      this.$router.push('/socios')
    },
  }
}
</script>
