import { defineConfig } from 'cypress';
// @ts-ignore
// import codeCoverageTask from '@cypress/code-coverage/task';
const codeCoverageTask = require('@cypress/code-coverage/task');

export default defineConfig({
  videosFolder: 'cypress/videos',
  screenshotsFolder: 'cypress/screenshots',
  fixturesFolder: 'cypress/fixtures',
  video: false,
  e2e: {
    // We've imported your old cypress plugins here.
    // You may want to clean this up later by importing these.
    setupNodeEvents(on, config) {
      codeCoverageTask(on, config);
      return require('./cypress/plugins/index.ts').default(on, config);
    },
    baseUrl: 'http://localhost:4200',
  },
});
