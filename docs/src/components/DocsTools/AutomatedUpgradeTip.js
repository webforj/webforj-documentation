import React from 'react';
import Admonition from '@theme/Admonition';
import Link from '@docusaurus/Link';
import Translate, { translate } from '@docusaurus/Translate';

export default function AutomatedUpgradeTip() {
  return (
    <Admonition
      type="tip"
      title={translate({
        id: 'automatedUpgradeTip.title',
        description: 'The title for the tip admonition for OpenRewrite',
        message: 'Automated upgrade available',
      })}
    >
      <Translate
        id='automatedUpgradeTip.message'
        description='The message of a tip indicating that you can use OpenRewrite to make updating easier'
        values={{
          articleLink: (
            <Link to='/docs/upgrading/automated-upgrades'>
              <Translate
                id='automatedUpgradeTip.automatedUpgradeLink'
                description='The text that links to the automated upgrade article'>
                Using OpenRewrite
              </Translate>
            </Link>
          ),
        }}>
        {'OpenRewrite recipes are available for this version to automatically update deprecated and removed APIs. See {articleLink} for setup instructions.'}
      </Translate>
    </Admonition>
  );
}

