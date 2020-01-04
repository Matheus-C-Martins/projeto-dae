<!-- eslint-disable -->
<template>
  <v-app id="inspier">
    <v-card>
      <v-card-title style="padding-bottom: 0px" class="headline"> O Meu Perfil </v-card-title>
      <v-card-text class="pa-0">
        <v-form align="center">
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Username: </h6> {{atleta.username}}
              </v-card>
            </v-col>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Nome: </h6> {{atleta.nome}}
              </v-card>
            </v-col>
          </v-row>
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Email: </h6> {{atleta.email}}
              </v-card>
            </v-col>
          </v-row>
        </v-form>
        <v-data-table
          :loading='loading'
          loading-text='A carregar modalidades... Aguarde um momento'
          expand-icon
          :headers='headers'
          :items='modalidades'
          class='elevation-1'
          no-data-text='Não está inscrito em nenhuma modalidade'>
          <template v-slot:top>
            <v-toolbar flat color='white'>
              <v-toolbar-title> Modalidades Inscrito: </v-toolbar-title>
              <v-spacer></v-spacer>
              <v-btn color='primary' dark class='mb-2' @click="logout()"> LOGOUT </v-btn>
            </v-toolbar>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
  </v-app>
</template>

<script>
/* eslint-disable */
export default {
  data() {
    return {
      loading: true,
      atleta: {},
      headers: [
        { text: 'Nome', value: 'nome', align: 'center', sortable: false },
        { text: 'Escalão', value: 'escalao', align: 'center', sortable: false }
      ],
      modalidades: []
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
  },
  created() {
    this.initialize();
  },
  watch: {
    dialog (val) {
      val || this.close();
    },
  },
  methods: {
    initialize() {
      this.$axios.$get(`/api/atletas/${this.username}`).then(atleta => (this.atleta = atleta || {}));
      this.getModalidades();
    },
    getModalidades() {
      this.$axios.$get(`/api/atletas/${this.username}/modalidades`).then(modalidades => (this.modalidades = modalidades || {}));
      this.loading = false;
    },
    async logout() {
      let promise = await this.$auth.logout('local');
      this.$toast.success("Obrigado por utilizar a nossa plataforma.");
      this.$router.push('/')
    }
  }
}
</script>
