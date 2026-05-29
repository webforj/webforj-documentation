// PrismJS theme using DWC CSS custom properties
// Colors are defined via --dwc-code-* vars in custom.scss

const theme = {
  plain: {
    color: 'var(--dwc-code-text)',
    backgroundColor: 'var(--dwc-code-bg)',
  },
  styles: [
    {
      types: ['keyword', 'operator.arrow', 'important', 'atrule'],
      style: { color: 'var(--dwc-code-keyword)' },
    },
    {
      types: ['comment', 'prolog'],
      style: { color: 'var(--dwc-code-comment)', fontStyle: 'italic' },
    },
    {
      types: ['punctuation'],
      style: { color: 'var(--dwc-code-punctuation)' },
    },
    {
      types: ['property', 'tag', 'boolean', 'number', 'constant', 'symbol', 'inserted', 'unit'],
      style: { color: 'var(--dwc-code-number)' },
    },
    {
      types: ['selector', 'attr-name', 'string', 'char', 'builtin', 'deleted'],
      style: { color: 'var(--dwc-code-string)' },
    },
    {
      types: ['operator', 'entity'],
      style: { color: 'var(--dwc-code-operator)' },
    },
    {
      types: ['function'],
      style: { color: 'var(--dwc-code-function)' },
    },
    {
      types: ['class-name'],
      style: { color: 'var(--dwc-code-class)' },
    },
    {
      types: ['regex'],
      style: { color: 'var(--dwc-code-regex)' },
    },
    {
      types: ['variable'],
      style: { color: 'var(--dwc-code-variable)' },
    },
    {
      types: ['keyword.control-flow', 'keyword.module', 'atrule.rule'],
      style: { color: 'var(--dwc-code-control)' },
    },
    {
      types: ['italic'],
      style: { fontStyle: 'italic' },
    },
    {
      types: ['namespace'],
      style: { opacity: 0.7 },
    },
  ],
};

module.exports = theme;
