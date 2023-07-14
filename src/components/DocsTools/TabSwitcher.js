/**@jsx jsx */
import React, {useEffect, useState } from 'react'
import { useLocation, withRouter } from '@docusaurus/router';
import { jsx, css } from '@emotion/react';
import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';
import Link from '@docusaurus/Link';

export default function TabSwitcher( { component } ) {

  const testStyles = css`
    /* font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif; */
    font-weight: unset;
  `

  // const location = useLocation();
  const [secondToLastValue, setSecondToLastValue] = useState('');

  // useEffect(() => {
  //   const currentPath = location.pathname;
  //   const pathSegments = currentPath.split('/');
  //   setSecondToLastValue(pathSegments.at(-2));
  // }, [location]);

  // const navigate = useNavigate();
  
  return (
    // <div onClick={(e)=> {
    //   const tab = e.target.closest('[role="tab"]')
    //   if(tab){
    //     console.log(tab)
    //     const label = tab.innerText
    //     if(label === 'Implementation'){
    //       // location.pathname='/docs/components/'
    //       navigate('/docs/components/' + component); 
    //       console.log("TEST 1")
    //     }
    //     else{
    //       navigate('/docs/components/styles' + component); 
    //       console.log("TEST2")
    //     }
    //   }
    // }}>
    //   <Tabs css={testStyles} defaultValue={secondToLastValue === 'styles' ? 'Styling' : "Implementation"}>
    //     <TabItem className='implementation' url={'/docs/components/' + component} value="Implementation" label="Implementation"></TabItem>
    //     <TabItem className={{styling:true}} url={'/docs/components/styles' + component} value="Styling" label="Styling" ></TabItem>
    //   </Tabs>
    // </div>

    <div>
      <Link to={'/docs/components/' + component}>Test1</Link>
      <Link to={'/docs/components/styles/' + component}>Test2</Link>
    </div>
  )
}